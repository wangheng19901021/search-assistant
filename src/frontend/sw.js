const CACHE_NAME = 'find-thing-v1';
const urlsToCache = [
  './',
  './index.html',
  './manifest.json',
  'https://cdn.jsdelivr.net/npm/qrcode@1.5.3/build/qrcode.min.js',
  'https://cdn.jsdelivr.net/npm/chart.js'
];

// 安装事件 - 缓存资源
self.addEventListener('install', event => {
  event.waitUntil(
    caches.open(CACHE_NAME)
      .then(cache => {
        console.log('缓存已打开');
        return cache.addAll(urlsToCache);
      })
      .catch(error => {
        console.log('缓存失败:', error);
      })
  );
});

// 激活事件 - 清理旧缓存
self.addEventListener('activate', event => {
  event.waitUntil(
    caches.keys().then(cacheNames => {
      return Promise.all(
        cacheNames.map(cacheName => {
          if (cacheName !== CACHE_NAME) {
            console.log('删除旧缓存:', cacheName);
            return caches.delete(cacheName);
          }
        })
      );
    })
  );
});

// 拦截网络请求
self.addEventListener('fetch', event => {
  event.respondWith(
    caches.match(event.request)
      .then(response => {
        // 如果在缓存中找到资源，返回缓存的版本
        if (response) {
          return response;
        }

        return fetch(event.request).then(response => {
          // 检查响应是否有效
          if (!response || response.status !== 200 || response.type !== 'basic') {
            return response;
          }

          // 克隆响应，因为响应流只能被消费一次
          const responseToCache = response.clone();

          caches.open(CACHE_NAME)
            .then(cache => {
              // 只缓存 GET 请求
              if (event.request.method === 'GET') {
                cache.put(event.request, responseToCache);
              }
            });

          return response;
        }).catch(() => {
          // 网络请求失败时的离线页面
          if (event.request.destination === 'document') {
            return caches.match('./index.html');
          }
        });
      })
  );
});

// 后台同步事件
self.addEventListener('sync', event => {
  if (event.tag === 'background-sync') {
    event.waitUntil(doBackgroundSync());
  }
});

// 执行后台同步
async function doBackgroundSync() {
  try {
    // 从 IndexedDB 获取待同步的数据
    const pendingData = await getPendingData();
    
    for (const data of pendingData) {
      try {
        // 尝试同步到服务器
        await syncToServer(data);
        // 同步成功后从本地删除
        await removePendingData(data.id);
      } catch (error) {
        console.log('同步失败:', error);
        // 保留数据，下次再试
      }
    }
  } catch (error) {
    console.log('后台同步失败:', error);
  }
}

// 获取待同步数据（这里是占位符，实际需要实现 IndexedDB 操作）
async function getPendingData() {
  // TODO: 从 IndexedDB 获取待同步的物品数据
  return [];
}

// 同步到服务器
async function syncToServer(data) {
  // TODO: 实现具体的同步逻辑
  const response = await fetch('/api/item/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data)
  });
  
  if (!response.ok) {
    throw new Error('同步失败');
  }
  
  return response.json();
}

// 删除待同步数据
async function removePendingData(id) {
  // TODO: 从 IndexedDB 删除已同步的数据
  console.log('删除待同步数据:', id);
}

// 推送通知事件
self.addEventListener('push', event => {
  const options = {
    body: event.data ? event.data.text() : '您有新的物品提醒',
    icon: '/manifest.json',
    badge: '/manifest.json',
    tag: 'find-thing-notification',
    renotify: true,
    requireInteraction: false,
  };

  event.waitUntil(
    self.registration.showNotification('寻物助手', options)
  );
});

// 通知点击事件
self.addEventListener('notificationclick', event => {
  event.notification.close();

  event.waitUntil(
    clients.openWindow('/')
  );
});