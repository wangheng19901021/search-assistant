# 寻物助手 Docker 部署指南

## 🐳 概述

寻物助手项目提供了完整的 Docker 部署解决方案，支持多种部署模式：

- **多容器部署**：前端(Nginx)、后端(Spring Boot)、数据库(MySQL)分别运行在独立容器中
- **单容器部署**：所有服务运行在一个容器中，适合小型部署或测试环境
- **开发模式**：专为开发环境优化的配置
- **生产模式**：针对生产环境的安全和性能优化

## 📋 系统要求

- Docker 20.10+
- Docker Compose 2.0+
- 可用端口：80、8080、3306
- 磁盘空间：至少 2GB

## 🚀 快速开始

### 1. 获取项目代码
```bash
git clone <repository-url>
cd Search\ Assistant
```

### 2. 一键部署
```bash
# 多容器部署（推荐）
./deploy.sh

# 单容器部署
./deploy.sh --single

# 开发模式
./deploy.sh --dev

# 生产模式
./deploy.sh --prod
```

### 3. 访问应用
- 前端页面：http://localhost
- 后端API：http://localhost:8080
- 数据库：localhost:3306

## 📖 详细说明

### 多容器部署
```bash
./deploy.sh --multi
```
- 前端：Nginx 容器提供静态文件服务
- 后端：Spring Boot 应用容器
- 数据库：MySQL 8.0 容器
- 缓存：Redis 容器（可选）

### 单容器部署
```bash
./deploy.sh --single
```
所有服务运行在一个容器中，使用 Supervisor 进行进程管理。

### 开发模式
```bash
./deploy.sh --dev
```
- 禁用自动重启
- 开启详细日志
- 便于调试的配置

### 生产模式  
```bash
./deploy.sh --prod
```
- 安全的密码配置
- 性能优化设置
- 数据持久化

## 🛠️ 管理命令

### 查看服务状态
```bash
docker-compose ps
```

### 查看日志
```bash
./deploy.sh --logs
```

### 停止服务
```bash
./deploy.sh --stop
```

### 重启服务
```bash
./deploy.sh --restart
```

### 清理环境
```bash
./deploy.sh --clean
```

## 🔧 配置说明

### 环境变量配置
创建 `.env` 文件进行自定义配置：
```env
# 数据库配置
MYSQL_ROOT_PASSWORD=your_root_password
MYSQL_PASSWORD=your_password
MYSQL_DATABASE=search_assistant

# 应用配置
SPRING_PROFILES_ACTIVE=docker
SERVER_PORT=8080
```

### 端口映射
- 80 → Nginx (前端)
- 8080 → Spring Boot (后端API)
- 3306 → MySQL (数据库)

### 数据持久化
- MySQL数据：`./mysql-data`
- 上传文件：`./uploads`
- 日志文件：`./logs`

## 🔍 故障排除

### 端口占用问题
```bash
# 检查端口占用
lsof -i :80
lsof -i :8080
lsof -i :3306

# 停止占用端口的服务
sudo kill -9 <PID>
```

### 容器启动失败
```bash
# 查看容器日志
docker-compose logs <service-name>

# 重新构建镜像
docker-compose up --build --force-recreate
```

### 数据库连接问题
```bash
# 检查MySQL服务状态
docker-compose exec mysql mysql -u findthing -p search_assistant

# 重置数据库
docker-compose down -v
docker-compose up -d
```

### 磁盘空间不足
```bash
# 清理未使用的镜像
docker system prune -a

# 查看磁盘使用情况
docker system df
```

## 📊 健康检查

部署脚本会自动进行健康检查：
- 前端服务：GET http://localhost/health
- 后端服务：GET http://localhost:8080/actuator/health

## 🔐 安全配置

### 生产环境安全建议
1. 修改默认密码
2. 启用HTTPS
3. 配置防火墙规则
4. 定期备份数据
5. 更新容器镜像

### 数据备份
```bash
# 数据库备份
docker-compose exec mysql mysqldump -u root -p search_assistant > backup.sql

# 恢复数据库
docker-compose exec -T mysql mysql -u root -p search_assistant < backup.sql
```

## 📈 性能优化

### MySQL优化
- 已配置适合的缓冲池大小
- 启用慢查询日志
- 优化连接池设置

### Nginx优化
- 启用Gzip压缩
- 静态文件缓存
- 请求限速配置

### Spring Boot优化
- JVM内存设置
- 连接池配置
- 缓存策略

## 🆘 获取帮助

```bash
# 查看部署脚本帮助
./deploy.sh --help

# 查看Docker Compose配置
docker-compose config

# 检查系统资源
docker stats
```

## 📝 默认账号信息

- **数据库用户**：findthing
- **数据库密码**：findthing123  
- **数据库名称**：search_assistant
- **管理员账号**：demo_user
- **邮箱**：demo@example.com

> ⚠️ **生产环境请务必修改默认密码！**

---

有任何问题请参考故障排除章节或联系技术支持。