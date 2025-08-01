# 🚀 寻物助手部署指南

## 📋 概述

本指南将详细说明如何部署优化后的寻物助手项目。项目已经过全面整理，支持多种部署方式和自动化Git拉取功能。

---

## 🎯 部署方式选择

### 🚀 方式一：本地部署（推荐新手）

#### 如果您已经有项目文件夹：

```bash
# 1. 进入您的项目目录
cd "/mnt/f/Work Space/Search Assistant"

# 2. 直接一键部署
./deployment/scripts/deploy.sh

# 3. 访问应用
# 前端: http://localhost
# 后端: http://localhost:8080
```

#### 如果您想从零开始（全新部署）：

```bash
# 1. 删除旧文件夹（如果需要）
rm -rf "/mnt/f/Work Space/Search Assistant"

# 2. 重新克隆项目
git clone https://github.com/wangheng19901021/search-assistant.git
cd search-assistant

# 3. 一键部署
./deployment/scripts/deploy.sh
```

### 🔄 方式二：自动更新部署（推荐）

如果您想确保使用最新版本：

```bash
# 在项目目录中运行
cd "/mnt/f/Work Space/Search Assistant"

# 自动拉取最新代码并部署
./deployment/scripts/deploy.sh --update
```

**功能特点**：
- ✅ 自动检测代码更新
- ✅ 智能暂存本地修改
- ✅ 显示版本更新信息
- ✅ 无缝更新部署

### 🎮 方式三：选择部署模式

```bash
# 多容器部署（推荐，功能完整）
./deployment/scripts/deploy.sh --multi

# 单容器部署（节省资源）
./deployment/scripts/deploy.sh --single

# 生产环境部署（更安全）
./deployment/scripts/deploy.sh --prod

# 开发环境部署（便于调试）
./deployment/scripts/deploy.sh --dev
```

---

## 📊 管理命令

部署完成后，您可以使用这些命令管理项目：

### 状态查看
```bash
# 查看服务状态和版本信息
./deployment/scripts/deploy.sh --status

# 查看详细服务日志
./deployment/scripts/deploy.sh --logs
```

### 服务管理
```bash
# 停止所有服务
./deployment/scripts/deploy.sh --stop

# 重启服务
./deployment/scripts/deploy.sh --restart

# 完全清理（慎用，会删除所有数据）
./deployment/scripts/deploy.sh --clean
```

### 帮助信息
```bash
# 查看所有可用选项
./deployment/scripts/deploy.sh --help
```

---

## 🌐 访问您的应用

部署成功后，打开浏览器访问：

- **🏠 主应用页面**: http://localhost
- **🔧 后端API**: http://localhost:8080
- **🗄️ 数据库管理**: localhost:3306

### 数据库连接信息
- **用户名**: `findthing`
- **密码**: `findthing123`
- **数据库名**: `search_assistant`

### 系统端口说明
- **端口 80**: 前端Nginx服务
- **端口 8080**: 后端Spring Boot API
- **端口 3306**: MySQL数据库服务

---

## 🎯 推荐的部署流程

对于大多数用户，推荐按以下步骤操作：

### 步骤1: 准备环境
```bash
# 确保Docker已安装并运行
docker --version
docker-compose --version
```

### 步骤2: 进入项目目录
```bash
cd "/mnt/f/Work Space/Search Assistant"
```

### 步骤3: 更新并部署
```bash
# 自动拉取最新代码并部署
./deployment/scripts/deploy.sh --update
```

### 步骤4: 等待部署完成
```
🚀 寻物助手 Docker 部署工具
================================
📦 检查代码更新...
⬇️  拉取最新代码...
🐳 多容器部署模式
📁 准备部署环境...
🎉 部署完成！
```

### 步骤5: 验证部署
```bash
# 检查服务状态
./deployment/scripts/deploy.sh --status
```

### 步骤6: 访问应用
打开浏览器访问 http://localhost

---

## 🔧 故障排除

### 常见问题及解决方案

#### 🚫 端口被占用
```bash
# 检查端口占用情况
sudo netstat -tlnp | grep :80
sudo netstat -tlnp | grep :8080
sudo netstat -tlnp | grep :3306

# 停止占用端口的服务
sudo systemctl stop apache2    # 停止Apache
sudo systemctl stop nginx      # 停止Nginx
sudo systemctl stop mysql      # 停止MySQL
```

#### 🐳 Docker权限问题
```bash
# 添加当前用户到Docker组
sudo usermod -aG docker $USER

# 重新加载组权限
newgrp docker

# 重启Docker服务
sudo systemctl restart docker
```

#### ❌ 部署失败
```bash
# 查看详细错误日志
./deployment/scripts/deploy.sh --logs

# 完全清理后重新部署
./deployment/scripts/deploy.sh --clean
./deployment/scripts/deploy.sh
```

#### 🔄 Git拉取失败
```bash
# 检查网络连接
ping github.com

# 检查Git配置
git remote -v

# 手动拉取代码
git pull origin main
```

#### 🐛 容器启动异常
```bash
# 查看容器状态
docker ps -a

# 查看特定容器日志
docker logs search-assistant-backend
docker logs search-assistant-frontend
docker logs search-assistant-mysql

# 重启特定容器
docker restart search-assistant-backend
```

---

## 📱 使用您的应用

部署成功后，您可以开始使用寻物助手：

### 🎯 基本操作

1. **添加物品**
   - 点击页面上的"添加物品"按钮
   - 填写物品信息（名称、分类、位置、描述）
   - 可选择上传物品图片
   - 点击"保存"添加物品

2. **搜索物品**
   - 使用顶部搜索框输入关键词
   - 支持按名称、分类、位置搜索
   - 实时显示搜索结果

3. **管理物品**
   - 点击物品卡片查看详情
   - 编辑物品信息
   - 删除不需要的物品
   - 生成物品二维码

4. **查看统计**
   - 切换到统计页面
   - 查看物品分布图表
   - 分析使用数据

### 🔧 高级功能

- **批量导入**: 支持Excel/CSV格式批量导入
- **数据导出**: 导出物品清单为Excel文件
- **二维码**: 为每个物品生成唯一二维码
- **历史记录**: 查看物品添加和修改历史
- **分类管理**: 自定义物品分类
- **位置管理**: 管理存储位置信息

---

## 🔐 生产环境部署

### 安全配置

如果要部署到生产环境，建议使用生产模式：

```bash
# 生产环境部署
./deployment/scripts/deploy.sh --prod
```

生产模式会自动：
- ✅ 创建安全的数据库密码配置
- ✅ 启用数据持久化
- ✅ 配置日志管理
- ✅ 优化性能设置

### 环境配置文件

生产模式会创建 `deployment/.env.prod` 文件：

```env
# 生产环境配置
MYSQL_ROOT_PASSWORD=your_secure_root_password_here
MYSQL_PASSWORD=your_secure_password_here
COMPOSE_PROJECT_NAME=search-assistant-prod
```

**重要**: 请修改默认密码为安全的密码！

---

## 📈 性能监控

### 查看系统资源使用

```bash
# 查看容器资源使用情况
docker stats

# 查看磁盘使用
df -h

# 查看内存使用
free -h
```

### 日志管理

```bash
# 查看实时日志
./deployment/scripts/deploy.sh --logs

# 查看特定服务日志
docker logs -f search-assistant-backend
docker logs -f search-assistant-mysql
```

---

## 🔄 更新和维护

### 定期更新

建议定期更新项目到最新版本：

```bash
# 每周或每月运行一次
./deployment/scripts/deploy.sh --update
```

### 数据备份

重要数据请定期备份：

```bash
# 备份MySQL数据
docker exec search-assistant-mysql mysqldump -u root -p search_assistant > backup.sql

# 备份上传文件
tar -czf uploads_backup.tar.gz uploads/
```

### 性能优化

- 定期清理不用的Docker镜像: `docker system prune`
- 监控磁盘空间使用情况
- 根据使用情况调整容器资源限制

---

## 🆘 获取帮助

### 文档资源

- 📖 [项目主文档](../README.md)
- 📁 [目录结构说明](../DIRECTORY_STRUCTURE.md)
- ⚡ [快速开始指南](QUICK_START.md)
- 🐳 [Docker部署详解](DOCKER_DEPLOYMENT.md)

### 技术支持

- 🐛 **问题反馈**: [GitHub Issues](https://github.com/wangheng19901021/search-assistant/issues)
- 💬 **社区讨论**: [GitHub Discussions](https://github.com/wangheng19901021/search-assistant/discussions)

### 紧急处理

如果遇到严重问题：

1. **立即停止服务**: `./deployment/scripts/deploy.sh --stop`
2. **查看错误日志**: `./deployment/scripts/deploy.sh --logs`
3. **备份重要数据**: 导出数据库和上传文件
4. **重新部署**: `./deployment/scripts/deploy.sh --clean && ./deployment/scripts/deploy.sh`

---

## 🎉 部署完成！

恭喜！您的寻物助手系统已成功部署。

### 🎯 下一步操作

1. **访问应用**: 打开 http://localhost
2. **添加第一个物品**: 开始使用系统
3. **探索功能**: 体验搜索、分类、统计等功能
4. **定期更新**: 使用 `--update` 参数保持最新版本

### 🌟 享受使用

现在您可以开始使用这个现代化的物品管理系统了！

- 📱 **移动友好**: 在手机上也能完美使用
- 🔍 **智能搜索**: 快速找到您的物品
- 📊 **数据分析**: 了解您的物品管理习惯
- 🔐 **安全可靠**: 数据安全存储

**祝您使用愉快！** 🎉