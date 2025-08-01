# 寻物助手 v2.0 - 智能物品管理系统

一个基于Spring Boot + HTML5的现代化智能物品管理应用，帮助用户轻松管理和查找个人物品。

## ✨ v2.0 新功能特性

### 🤖 智能功能
- **智能搜索系统**: 语音搜索、扫码搜索、智能建议和搜索历史
- **快速录入模式**: 智能文本解析、连拍识别、语音录入三种模式
- **智能推荐**: 基于用户行为的分类、位置和时间推荐
- **数据分析**: 用户行为分析、使用习惯统计和个性化建议

### 📱 移动端优化
- **手势操作**: 左滑显示操作菜单、下拉刷新、双击放大图片
- **长按快捷**: 长按屏幕显示快捷操作面板
- **触感反馈**: 支持设备震动反馈
- **移动适配**: 完美的触屏交互体验

### 💾 数据安全
- **自动备份**: 每30分钟自动备份用户数据
- **手动备份**: 支持创建命名备份和备份描述
- **数据导出**: 完整的数据导出和导入功能
- **历史管理**: 保留最近20个备份记录

### 🔍 搜索增强
- **多模式搜索**: 文本、语音、扫码三种搜索方式
- **智能建议**: 基于历史搜索的智能推荐
- **实时筛选**: 支持分类、位置、时间等多维度筛选
- **搜索历史**: 记录和快速调用搜索历史

## 🎯 核心功能

- 📱 **物品管理**: 添加、编辑、删除物品，支持图片上传
- 🏷️ **分类管理**: 灵活的分类系统，支持系统预设和自定义分类
- 📍 **位置管理**: 多级位置管理，快速定位物品存放地
- 📊 **统计分析**: 可视化图表展示物品分布和趋势
- 🔍 **高级搜索**: 多条件筛选和排序功能
- 📤 **批量操作**: 支持CSV/JSON格式的数据导入导出
- 📱 **PWA支持**: 可安装到桌面，支持离线使用
- 🎯 **二维码**: 为物品生成二维码标签
- 📝 **历史记录**: 完整的物品变更历史追踪
- 📱 **移动优先**: 响应式设计，完美适配手机和平板

## 🛠️ 技术栈

### 后端
- **Spring Boot 3.1.5** - 主框架
- **MyBatis Plus 3.5.8** - ORM框架
- **MySQL 8.0+** - 数据库
- **Maven** - 项目管理工具

### 前端
- **HTML5 + CSS3 + JavaScript** - 原生Web技术
- **Chart.js** - 图表库
- **QRCode.js** - 二维码生成
- **Service Worker** - PWA支持
- **Web Speech API** - 语音识别
- **Touch Events API** - 移动端手势
- **MediaDevices API** - 摄像头访问
- **Local Storage** - 本地数据存储

### v2.0 新增技术
- **智能文本解析** - 自然语言处理
- **用户行为分析** - 数据挖掘和个性化推荐
- **手势识别系统** - 移动端交互优化
- **自动备份系统** - 数据安全保障

## 📋 环境要求

- **Java**: JDK 11或以上版本
- **MySQL**: 8.0或以上版本
- **Maven**: 3.6或以上版本（可选，项目包含Maven Wrapper）

## 🚀 快速开始

### 1. 克隆项目
```bash
git clone <repository-url>
cd search-assistant
```

### 2. 配置数据库

在MySQL中创建数据库：
```sql
CREATE DATABASE search_assistant CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 配置应用
编辑 `backend/src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/search_assistant?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
```

### 4. 启动应用
```bash
# 使用启动脚本（推荐）
./start.sh

# 或手动启动
cd backend
./mvnw spring-boot:run
```

### 5. 访问应用
- 应用地址: http://localhost:8080
- 前端页面: http://localhost:8080/index.html

## 🎮 v2.0 使用指南

### 智能搜索功能
- **语音搜索**: 点击搜索框右侧的 🎤 图标，说出要搜索的内容
- **扫码搜索**: 点击搜索框右侧的 📷 图标，扫描物品二维码
- **智能建议**: 输入时自动显示相关建议，点击即可快速搜索
- **搜索历史**: 聚焦搜索框时显示最近搜索记录

### 快速录入模式
1. 点击右下角的 **+** 悬浮按钮打开快速添加
2. 选择录入模式：
   - **🤖 智能识别**: 输入自然语言描述，系统自动解析
   - **📷 连拍模式**: 连续拍照多个物品，批量识别
   - **🎤 语音录入**: 语音描述物品信息，自动转换

### 移动端手势操作
- **左滑卡片**: 在物品卡片上左滑显示编辑、二维码、删除操作
- **下拉刷新**: 在物品列表页面下拉可刷新数据
- **双击图片**: 双击物品图片可放大查看
- **长按屏幕**: 长按空白区域显示快捷操作面板

### 数据备份管理
1. 长按屏幕打开快捷操作面板
2. 选择"数据备份"进入备份管理
3. 支持立即备份、导出备份、恢复历史备份
4. 系统每30分钟自动创建备份

## 📁 项目结构

```
search-assistant/
├── backend/                    # 后端Spring Boot应用
│   ├── src/main/java/         # Java源码
│   │   └── com/findthing/
│   │       ├── controller/    # 控制器层
│   │       ├── service/       # 业务逻辑层
│   │       ├── mapper/        # 数据访问层
│   │       ├── entity/        # 实体类
│   │       ├── dto/          # 数据传输对象
│   │       ├── config/       # 配置类
│   │       └── utils/        # 工具类
│   ├── src/main/resources/   # 资源文件
│   │   ├── application.yml   # 应用配置
│   │   ├── schema.sql       # 数据库表结构
│   │   └── data.sql         # 初始化数据
│   └── pom.xml              # Maven配置
├── frontend/                 # 前端文件
│   ├── index.html           # 主页面
│   ├── manifest.json        # PWA配置
│   └── sw.js               # Service Worker
├── start.sh                # 启动脚本
├── README.md               # 项目说明
├── DEPLOYMENT_GUIDE.md     # 部署指南
└── requirements.md         # 需求说明
```

## 🎯 API接口

### 物品管理
- `GET /api/item/list` - 获取物品列表
- `POST /api/item/add` - 添加物品
- `PUT /api/item/{id}` - 更新物品
- `DELETE /api/item/{id}` - 删除物品
- `GET /api/item/{id}` - 获取物品详情

### 分类管理
- `GET /api/category/list` - 获取分类列表
- `POST /api/category/add` - 添加分类
- `DELETE /api/category/{id}` - 删除分类

### 位置管理
- `GET /api/location/list` - 获取位置列表
- `POST /api/location/add` - 添加位置
- `DELETE /api/location/{id}` - 删除位置

### 文件上传
- `POST /api/upload/image` - 上传图片

### 历史记录
- `GET /api/item/{id}/history` - 获取物品历史记录
- `POST /api/item/{id}/history` - 添加历史记录

### v2.0 新增API
- `POST /api/search/voice` - 语音搜索处理
- `POST /api/search/suggestions` - 获取搜索建议
- `POST /api/item/batch` - 批量添加物品
- `GET /api/analytics/behavior` - 获取用户行为分析
- `GET /api/analytics/recommendations` - 获取智能推荐
- `POST /api/backup/create` - 创建数据备份
- `GET /api/backup/list` - 获取备份列表
- `POST /api/backup/restore` - 恢复数据备份

## 🔧 配置说明

### 数据库配置
项目支持自动创建数据库表和初始化数据。首次启动时会自动执行：
- `schema.sql` - 创建表结构
- `data.sql` - 插入初始数据

### 文件上传配置
默认上传路径：`./uploads/`
支持的图片格式：JPG, PNG, GIF
最大文件大小：5MB

### PWA配置
- 支持离线使用
- 可安装到设备桌面
- 自动缓存静态资源

### v2.0 配置增强
- **语音识别**: 支持中文语音识别，需要HTTPS环境
- **摄像头访问**: 需要用户授权摄像头权限
- **本地存储**: 自动存储用户行为数据和备份
- **自动备份**: 每30分钟自动备份，保留最近10个自动备份
- **手势识别**: 自动检测移动设备并启用手势功能

## 🌟 v2.0 技术亮点

### 智能化程度
- 自然语言处理能力，支持中文物品描述解析
- 基于机器学习的用户行为分析
- 个性化推荐系统，越用越智能

### 用户体验
- 原生应用般的交互体验
- 完整的移动端手势支持
- 直观的语音和视觉交互

### 数据安全
- 多层次备份保护
- 本地化数据存储
- 完整的操作历史记录

### 性能优化
- 响应式界面设计
- 智能缓存机制
- 异步数据处理

## 🚀 部署

详细部署说明请参考 [DEPLOYMENT_GUIDE.md](DEPLOYMENT_GUIDE.md)

## 🤝 贡献

欢迎提交Issue和Pull Request来帮助改进项目。

## 📄 许可证

本项目采用MIT许可证。