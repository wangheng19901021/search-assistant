-- 初始化基础数据
-- 插入系统默认数据和演示数据

-- 创建系统默认分类
INSERT IGNORE INTO categories (id, name, description, icon, color, is_system, create_time, update_time) VALUES
(1, '电子产品', '手机、电脑、相机等电子设备', '📱', '#007AFF', 1, NOW(), NOW()),
(2, '书籍文具', '图书、笔记本、文具用品', '📚', '#34C759', 1, NOW(), NOW()),
(3, '生活用品', '日常生活必需品', '🏠', '#FF9500', 1, NOW(), NOW()),
(4, '服装配饰', '衣物、鞋帽、饰品', '👔', '#AF52DE', 1, NOW(), NOW()),
(5, '运动健身', '运动器材、健身用品', '⚽', '#FF2D92', 1, NOW(), NOW()),
(6, '工具设备', '维修工具、专业设备', '🔧', '#8E8E93', 1, NOW(), NOW()),
(7, '其他物品', '未分类物品', '📦', '#6D6D70', 1, NOW(), NOW());

-- 创建系统默认位置
INSERT IGNORE INTO locations (id, name, description, icon, create_time, update_time) VALUES
(1, '客厅', '家庭活动中心区域', '🛋️', NOW(), NOW()),
(2, '卧室', '休息睡眠区域', '🛏️', NOW(), NOW()),
(3, '厨房', '烹饪用餐区域', '🍳', NOW(), NOW()),
(4, '书房', '学习办公区域', '📚', NOW(), NOW()),
(5, '阳台', '晾晒休闲区域', '🌿', NOW(), NOW()),
(6, '储物间', '物品收纳区域', '📦', NOW(), NOW()),
(7, '车内', '汽车内部空间', '🚗', NOW(), NOW()),
(8, '办公室', '工作场所', '🏢', NOW(), NOW()),
(9, '其他位置', '未指定位置', '📍', NOW(), NOW());

-- 创建演示用户
INSERT IGNORE INTO users (id, username, email, create_time, update_time) VALUES
(10001, 'demo_user', 'demo@example.com', NOW(), NOW());

-- 创建系统默认标签
INSERT IGNORE INTO tags (id, name, color, create_time) VALUES
(1, '重要', '#FF3B30'),
(2, '常用', '#007AFF'),
(3, '贵重', '#FF9500'),
(4, '易丢', '#FF2D92'),
(5, '借出', '#8E8E93'),
(6, '待修理', '#FFCC00'),
(7, '收藏', '#AF52DE'),
(8, '礼品', '#34C759');

-- 创建演示物品数据
INSERT IGNORE INTO items (id, user_id, name, category_id, location_id, description, status, tags, purchase_price, notes, create_time, update_time) VALUES
(1, 10001, 'iPhone 15 Pro', 1, 1, '深空黑色，256GB存储，Face ID解锁', 'normal', JSON_ARRAY('重要', '贵重'), 7999.00, '主力手机，包装盒在书房抽屉', NOW(), NOW()),
(2, 10001, '《三体》全集', 2, 4, '刘慈欣科幻小说三部曲，精装版', 'normal', JSON_ARRAY('收藏'), 158.00, '科幻经典，值得反复阅读', NOW(), NOW()),
(3, 10001, '保温杯', 3, 3, '不锈钢材质，500ml容量，黑色磨砂', 'normal', JSON_ARRAY('常用'), 89.00, '日常饮水使用', NOW(), NOW()),
(4, 10001, 'AirPods Pro', 1, 2, '苹果无线耳机，主动降噪', 'normal', JSON_ARRAY('重要', '常用'), 1999.00, '通勤必备，充电盒经常找不到', NOW(), NOW()),
(5, 10001, '机械键盘', 1, 4, '青轴机械键盘，RGB背光', 'normal', JSON_ARRAY('常用'), 599.00, '编程工作使用，手感很好', NOW(), NOW());

-- 创建演示搜索历史
INSERT IGNORE INTO search_history (user_id, search_keyword, search_type, result_count, search_time) VALUES
(10001, '手机', 'keyword', 2, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(10001, '电子产品', 'category', 3, DATE_SUB(NOW(), INTERVAL 2 DAY)),
(10001, '书房', 'location', 2, DATE_SUB(NOW(), INTERVAL 3 DAY));

-- 创建系统配置
INSERT IGNORE INTO system_config (config_key, config_value, config_description, is_system) VALUES
('app_name', '寻物助手', '应用名称', 1),
('version', '1.0.0', '当前版本', 1),
('max_upload_size', '10485760', '最大上传文件大小(字节)', 1),
('allowed_file_types', 'jpg,jpeg,png,gif,webp', '允许上传的文件类型', 1),
('backup_enabled', 'true', '是否启用自动备份', 1),
('search_history_days', '30', '搜索历史保留天数', 1);

-- 更新自增ID起始值
ALTER TABLE categories AUTO_INCREMENT = 100;
ALTER TABLE locations AUTO_INCREMENT = 100; 
ALTER TABLE tags AUTO_INCREMENT = 100;
ALTER TABLE items AUTO_INCREMENT = 1000;
ALTER TABLE users AUTO_INCREMENT = 10002;