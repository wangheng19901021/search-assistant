# 🚀 寻物助手项目服务器部署完整指南

## 📋 概述

本指南将详细说明如何将寻物助手项目部署到服务器，包括从项目发布到最终运行的完整流程。

---

## 🔄 方案选择

根据您的情况，提供两种主要方案：

### 📦 **方案一：直接文件传输部署（推荐新手）**
- 适用于：没有Git仓库，快速部署
- 优点：简单直接，无需学习Git
- 缺点：后续更新稍复杂

### 🌐 **方案二：Git仓库部署（推荐生产）**
- 适用于：长期维护，团队协作
- 优点：版本控制，便于更新
- 缺点：需要学习Git基础

---

## 📦 方案一：直接文件传输部署

### 🔧 **服务器环境准备**

#### 1. 服务器基础要求
```bash
# 系统要求
- Ubuntu 20.04+ / CentOS 7+ / Debian 10+
- 内存：最少 2GB，推荐 4GB+
- 磁盘：最少 10GB 可用空间
- 端口：80, 8080, 3306 需要开放
```

#### 2. 安装Docker（服务器端）
```bash
# Ubuntu/Debian
curl -fsSL https://get.docker.com | sh
sudo usermod -aG docker $USER
sudo systemctl enable docker
sudo systemctl start docker

# 安装Docker Compose
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

# 验证安装
docker version
docker-compose version
```

### 📁 **项目文件准备**

#### 1. 本地项目打包
```bash
# 在本地 Windows 上
cd "F:\\Work Space\\Search Assistant"

# 创建部署包（排除不必要文件）
# 方法1：使用7-Zip或WinRAR创建压缩包
# 选择以下文件夹：
- src/
- deployment/
- docs/
- DIRECTORY_STRUCTURE.md

# 或者方法2：使用命令行
tar -czf search-assistant.tar.gz src/ deployment/ docs/ DIRECTORY_STRUCTURE.md
```

#### 2. 上传到服务器
```bash
# 方法1：使用SCP上传
scp search-assistant.tar.gz username@your-server-ip:/home/username/

# 方法2：使用SFTP工具
# - FileZilla
# - WinSCP  
# - MobaXterm

# 方法3：使用rsync
rsync -avz --progress search-assistant.tar.gz username@your-server-ip:/home/username/
```

### 🚀 **服务器部署步骤**

#### 1. 解压项目文件
```bash
# SSH连接到服务器
ssh username@your-server-ip

# 解压项目
cd /home/username
tar -xzf search-assistant.tar.gz
cd search-assistant

# 查看目录结构
ls -la
```

#### 2. 配置权限
```bash
# 设置脚本执行权限
chmod +x deployment/scripts/deploy.sh

# 创建必要目录
mkdir -p logs uploads
sudo chown -R $USER:$USER logs uploads
```

#### 3. 一键部署
```bash
# 进入部署目录
cd deployment/scripts

# 查看部署选项
./deploy.sh --help

# 开始部署（推荐多容器模式）
./deploy.sh

# 或者单容器模式（资源较少时）
./deploy.sh --single
```

#### 4. 验证部署
```bash
# 检查容器状态
docker ps

# 检查服务健康状态
curl http://localhost/health
curl http://localhost:8080/actuator/health

# 查看日志
./deploy.sh --logs
```

---

## 🌐 方案二：Git仓库部署

### 📚 **Git仓库创建**

#### 1. 初始化Git仓库
```bash
# 在项目根目录
cd "F:\\Work Space\\Search Assistant"

# 初始化Git
git init

# 创建.gitignore
echo "logs/
uploads/
*.log
.env.prod
node_modules/
target/
.DS_Store
Thumbs.db" > .gitignore

# 添加所有文件
git add .
git commit -m "Initial commit: 寻物助手项目

🎉 项目特性:
- Spring Boot 3.1.5 后端
- 单页面前端应用 (4,617行代码)
- Docker 一键部署
- MySQL 8.0 数据库
- 完整的文档和部署脚本

🐳 部署方式:
- 多容器部署（推荐）
- 单容器部署
- 开发/生产环境支持"
```

#### 2. 创建远程仓库
```bash
# 选择平台：
# - GitHub: https://github.com
# - Gitee:  https://gitee.com  
# - GitLab: https://gitlab.com

# 创建新仓库，命名如：search-assistant 或 寻物助手

# 添加远程仓库
git remote add origin https://github.com/yourusername/search-assistant.git

# 推送代码
git branch -M main
git push -u origin main
```

### 🚀 **服务器Git部署**

#### 1. 克隆项目
```bash
# SSH连接服务器
ssh username@your-server-ip

# 克隆项目
git clone https://github.com/yourusername/search-assistant.git
cd search-assistant

# 或者从Gitee克隆（国内更快）
git clone https://gitee.com/yourusername/search-assistant.git
```

#### 2. 一键部署
```bash
# 设置权限
chmod +x deployment/scripts/deploy.sh

# 部署
cd deployment/scripts
./deploy.sh
```

#### 3. 后续更新
```bash
# 拉取最新代码
git pull origin main

# 重新部署
./deployment/scripts/deploy.sh --restart
```

---

## 🔧 生产环境配置

### 🔐 **安全配置**

#### 1. 修改默认密码
```bash
# 创建生产环境配置
cd deployment
cat > .env.prod << EOF
# 生产环境安全配置
MYSQL_ROOT_PASSWORD=YourSecureRootPassword123!
MYSQL_PASSWORD=YourSecurePassword123!
COMPOSE_PROJECT_NAME=search-assistant-prod
EOF

# 生产部署
./scripts/deploy.sh --prod
```

#### 2. 防火墙配置
```bash
# Ubuntu/Debian
sudo ufw allow 22    # SSH
sudo ufw allow 80    # HTTP
sudo ufw allow 443   # HTTPS
sudo ufw enable

# CentOS/RHEL
sudo firewall-cmd --permanent --add-port=22/tcp
sudo firewall-cmd --permanent --add-port=80/tcp
sudo firewall-cmd --permanent --add-port=443/tcp
sudo firewall-cmd --reload
```

### 🌐 **域名配置（可选）**

#### 1. 域名解析
```bash
# 在域名服务商设置A记录
# example.com -> your-server-ip
```

#### 2. Nginx配置更新
```bash
# 更新前端Nginx配置
vim src/frontend/nginx.conf

# 添加域名配置
server {
    listen 80;
    server_name your-domain.com;
    # ... 其他配置
}
```

### 📜 **SSL证书配置（可选）**

#### 1. 申请免费证书
```bash
# 安装certbot
sudo apt install certbot python3-certbot-nginx

# 申请证书
sudo certbot --nginx -d your-domain.com
```

#### 2. 更新Docker配置
```bash
# 修改docker-compose.yml
# 添加SSL证书挂载
volumes:
  - /etc/letsencrypt:/etc/nginx/ssl:ro
```

---

## 📊 监控和维护

### 📈 **系统监控**

#### 1. 服务状态检查
```bash
# 创建监控脚本
cat > ~/check-services.sh << 'EOF'
#!/bin/bash
echo "=== 服务状态检查 ==="
echo "时间: $(date)"
echo ""

echo "Docker容器状态:"
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
echo ""

echo "磁盘使用情况:"
df -h
echo ""

echo "内存使用情况:"
free -h
echo ""

echo "前端服务检查:"
curl -s -o /dev/null -w "%{http_code}" http://localhost/
echo ""

echo "后端服务检查:"
curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/actuator/health
echo ""
EOF

chmod +x ~/check-services.sh
```

#### 2. 设置定时任务
```bash
# 添加定时检查
crontab -e

# 每小时检查一次服务状态
0 * * * * /home/username/check-services.sh >> /var/log/service-check.log 2>&1
```

### 🔄 **备份策略**

#### 1. 数据库备份
```bash
# 创建备份脚本
cat > ~/backup-database.sh << 'EOF'
#!/bin/bash
BACKUP_DIR="/home/username/backups"
DATE=$(date +%Y%m%d_%H%M%S)

mkdir -p $BACKUP_DIR

# 备份数据库
docker exec search-assistant-mysql mysqldump -u root -proot123456 search_assistant > $BACKUP_DIR/database_$DATE.sql

# 保留最近7天的备份
find $BACKUP_DIR -name "database_*.sql" -mtime +7 -delete

echo "数据库备份完成: database_$DATE.sql"
EOF

chmod +x ~/backup-database.sh

# 每天凌晨2点备份
crontab -e
0 2 * * * /home/username/backup-database.sh
```

#### 2. 文件备份
```bash
# 备份上传文件和配置
tar -czf ~/backups/files_$(date +%Y%m%d).tar.gz ~/search-assistant/deployment/uploads ~/search-assistant/deployment/.env.prod
```

---

## 🆘 故障排除

### ❗ **常见问题**

#### 1. 端口占用
```bash
# 检查端口占用
sudo netstat -tlnp | grep :80
sudo netstat -tlnp | grep :8080
sudo netstat -tlnp | grep :3306

# 停止占用端口的服务
sudo systemctl stop apache2  # 如果有Apache
sudo systemctl stop nginx    # 如果有Nginx
```

#### 2. 权限问题
```bash
# 修复文件权限
sudo chown -R $USER:$USER ~/search-assistant
chmod +x ~/search-assistant/deployment/scripts/deploy.sh
```

#### 3. 容器启动失败
```bash
# 查看容器日志
docker logs search-assistant-mysql
docker logs search-assistant-backend
docker logs search-assistant-frontend

# 重新构建
cd ~/search-assistant/deployment/scripts
./deploy.sh --clean
./deploy.sh
```

#### 4. 数据库连接问题
```bash
# 进入MySQL容器
docker exec -it search-assistant-mysql mysql -u root -p

# 检查数据库
SHOW DATABASES;
USE search_assistant;
SHOW TABLES;
```

---

## 📞 技术支持

### 🔍 **日志查看**
```bash
# 查看所有服务日志
~/search-assistant/deployment/scripts/deploy.sh --logs

# 查看特定服务日志
docker logs -f search-assistant-backend
docker logs -f search-assistant-mysql
docker logs -f search-assistant-frontend
```

### 📊 **性能监控**
```bash
# 查看资源使用
docker stats

# 系统资源
htop
iotop
```

---

## 🎯 快速参考

### 常用命令
```bash
# 部署项目
./deployment/scripts/deploy.sh

# 查看状态
docker ps

# 查看日志
./deployment/scripts/deploy.sh --logs

# 停止服务
./deployment/scripts/deploy.sh --stop

# 重启服务
./deployment/scripts/deploy.sh --restart

# 清理环境
./deployment/scripts/deploy.sh --clean
```

### 访问地址
- 🌐 前端应用：http://your-server-ip
- 📡 后端API：http://your-server-ip:8080
- 🗄️ 数据库：your-server-ip:3306

---

按照这个指南，您就可以成功地将寻物助手项目部署到服务器并稳定运行！