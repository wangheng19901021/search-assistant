#!/bin/bash

echo "🚀 启动寻物助手一体化容器..."

# 初始化MySQL
if [ ! -d "/var/lib/mysql/mysql" ]; then
    echo "📦 初始化MySQL数据库..."
    mysqld --initialize-insecure --user=mysql --datadir=/var/lib/mysql
    
    # 启动MySQL进行初始化
    mysqld_safe --user=mysql &
    sleep 10
    
    # 创建数据库和用户
    mysql -u root << EOF
CREATE DATABASE IF NOT EXISTS search_assistant CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'findthing'@'%' IDENTIFIED BY 'findthing123';
GRANT ALL PRIVILEGES ON search_assistant.* TO 'findthing'@'%';
FLUSH PRIVILEGES;
EOF

    # 执行初始化SQL脚本
    if [ -d "/docker-entrypoint-initdb.d" ]; then
        for f in /docker-entrypoint-initdb.d/*.sql; do
            if [ -f "$f" ]; then
                echo "🗄️ 执行SQL脚本: $f"
                mysql -u root search_assistant < "$f"
            fi
        done
    fi
    
    # 停止MySQL，让supervisor重新启动
    mysqladmin -u root shutdown
    sleep 5
fi

echo "✅ MySQL初始化完成"

# 等待一段时间确保所有服务准备就绪
echo "⏳ 等待服务启动..."

# 启动supervisor管理所有服务
exec /usr/bin/supervisord -c /etc/supervisor/conf.d/supervisord.conf