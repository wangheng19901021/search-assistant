# ⚡ 寻物助手快速开始指南

## 🎯 部署方式选择

### 🚀 方式一：Git克隆部署（推荐）

```bash
# 1. 克隆项目
git clone https://github.com/wangheng19901021/search-assistant.git
cd search-assistant

# 2. 一键部署
./deployment/scripts/deploy.sh

# 3. 访问应用
# 前端: http://localhost
# 后端: http://localhost:8080
```

### 🔄 方式二：自动更新部署

```bash
# 如果已经克隆过项目，可以直接更新并部署
./deployment/scripts/deploy.sh --update
```

---

## 🎮 常用命令

### 基础操作
```bash
# 默认多容器部署
./deployment/scripts/deploy.sh

# 单容器部署（节省资源）
./deployment/scripts/deploy.sh --single

# 生产环境部署
./deployment/scripts/deploy.sh --prod

# 开发环境部署
./deployment/scripts/deploy.sh --dev
```

### 管理操作
```bash
# 查看服务状态
./deployment/scripts/deploy.sh --status

# 查看服务日志  
./deployment/scripts/deploy.sh --logs

# 停止所有服务
./deployment/scripts/deploy.sh --stop

# 重启服务
./deployment/scripts/deploy.sh --restart

# 清理环境
./deployment/scripts/deploy.sh --clean
```

---

## 🔧 系统要求

### 最低要求
- **Docker**: 20.10+
- **Docker Compose**: 2.0+
- **内存**: 2GB
- **磁盘**: 5GB

### 端口需求
- `80` - 前端服务
- `8080` - 后端API
- `3306` - MySQL数据库

---

## 🏠 访问地址

部署完成后：
- 🌐 **前端应用**: http://localhost
- 📡 **后端API**: http://localhost:8080
- 🗄️ **数据库**: localhost:3306

### 数据库信息
- **用户名**: `findthing`
- **密码**: `findthing123`
- **数据库**: `search_assistant`

---

## 🔍 故障排除

### 常见问题

#### 端口被占用
```bash
# 检查端口占用
sudo netstat -tlnp | grep :80
sudo netstat -tlnp | grep :8080

# 停止占用服务
sudo systemctl stop apache2  # 停止Apache
sudo systemctl stop nginx    # 停止Nginx
```

#### Docker权限问题
```bash
# 添加Docker权限
sudo usermod -aG docker $USER
newgrp docker
```

#### 服务启动失败
```bash
# 查看详细日志
./deployment/scripts/deploy.sh --logs

# 重新部署
./deployment/scripts/deploy.sh --clean
./deployment/scripts/deploy.sh
```

---

## 📊 功能特性

### 🔍 智能搜索
- 关键词搜索
- 分类筛选
- 位置查找
- 标签搜索

### 📱 响应式设计
- 手机端适配
- 平板端优化
- 桌面端完整体验

### 💾 数据管理
- 物品信息管理
- 图片上传支持
- 数据导出功能
- 自动备份

---

## 🎉 开始使用

1. **添加第一个物品**
   - 点击"添加物品"按钮
   - 填写物品信息
   - 选择分类和位置
   - 上传物品图片

2. **搜索物品**
   - 使用顶部搜索框
   - 输入关键词即时搜索
   - 使用分类和位置筛选

3. **管理物品**
   - 点击物品卡片查看详情
   - 编辑物品信息
   - 标记物品状态

---

## 📞 获取帮助

- 📖 **项目文档**: [README.md](../README.md)
- 📁 **目录说明**: [DIRECTORY_STRUCTURE.md](../DIRECTORY_STRUCTURE.md)
- 🐛 **问题反馈**: [GitHub Issues](https://github.com/wangheng19901021/search-assistant/issues)

---

**🎯 提示**: 首次启动可能需要几分钟时间下载Docker镜像，请耐心等待！