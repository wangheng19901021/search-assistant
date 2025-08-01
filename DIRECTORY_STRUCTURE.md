# 📁 项目目录结构说明

## 🎯 总体架构

寻物助手项目采用清晰的分层目录结构，将源码、部署配置和文档分离管理：

```
Search Assistant/
├── src/                    # 源代码目录
├── deployment/             # 部署配置目录  
├── docs/                   # 核心文档目录
├── README.md              # 项目总览
└── DIRECTORY_STRUCTURE.md # 目录结构说明
```

---

## 📂 详细目录结构

### 🔧 src/ - 源代码目录
```
src/
├── backend/               # Spring Boot 后端源码
│   ├── src/main/java/    # Java 源代码
│   │   └── com/findthing/
│   │       ├── controller/    # REST API 控制器
│   │       ├── service/       # 业务逻辑层
│   │       ├── entity/        # 数据实体类
│   │       ├── mapper/        # MyBatis 映射器
│   │       ├── dto/           # 数据传输对象
│   │       ├── config/        # 配置类
│   │       └── utils/         # 工具类
│   ├── src/main/resources/    # 资源文件
│   │   ├── application.yml    # 应用配置
│   │   ├── schema.sql         # 数据库表结构
│   │   └── data.sql          # 初始数据
│   ├── Dockerfile            # 后端容器构建文件
│   ├── pom.xml              # Maven 项目配置
│   ├── mvnw                 # Maven Wrapper 脚本
│   └── mvnw.cmd             # Windows Maven Wrapper
└── frontend/              # 前端源码
    ├── index.html           # 主页面文件 (4,617行核心代码)
    ├── sw.js               # Service Worker
    ├── manifest.json       # PWA 应用配置
    ├── nginx.conf          # Nginx 配置
    └── Dockerfile          # 前端容器构建文件
```

### 🚀 deployment/ - 部署配置目录
```
deployment/
├── docker-compose.yml        # 多容器编排配置 (推荐)
├── docker-compose.single.yml # 单容器部署配置
├── scripts/                  # 部署脚本
│   └── deploy.sh            # 主部署脚本 (支持Git自动拉取)
├── docker/                   # Docker 配置文件
│   └── Dockerfile.all-in-one # 单容器镜像构建文件
└── configs/                  # 配置文件
    ├── init-sql/            # 数据库初始化脚本
    │   ├── 01-schema.sql    # 数据库表结构 (9个核心表)
    │   └── 02-init-data.sql # 基础数据插入
    ├── mysql.cnf           # MySQL 优化配置
    ├── start.sh            # 容器启动脚本
    └── supervisord.conf    # 进程管理配置
```

### 📚 docs/ - 核心文档目录
```
docs/
├── QUICK_START.md          # 快速开始指南
├── DOCKER_DEPLOYMENT.md    # Docker 部署指南
├── CHANGELOG.md            # 版本更新日志
└── CLAUDE.md              # 开发历程记录
```

---

## 🎯 各目录作用说明

### 📁 **src/** - 应用源码
- **用途**: 存放所有应用程序源代码
- **包含**: 前端HTML/JS/CSS、后端Java代码
- **特点**: 纯源码，与部署环境无关

### 📁 **deployment/** - 部署配置
- **用途**: 包含所有部署相关的配置和脚本
- **包含**: Docker配置、数据库脚本、部署工具
- **特点**: 环境相关，支持多种部署方式

### 📁 **docs/** - 项目文档
- **用途**: 存放精简的核心文档
- **包含**: 使用指南、部署文档
- **特点**: 简洁明了，便于用户快速上手

---

## 🚀 使用指南

### 🔨 开发者使用
```bash
# 查看源码
cd src/backend          # 后端 Spring Boot 代码
cd src/frontend         # 前端单页面应用

# 本地开发
cd src/backend && ./mvnw spring-boot:run
```

### 🚀 部署人员使用
```bash
# 一键部署
./deployment/scripts/deploy.sh

# 更新并部署
./deployment/scripts/deploy.sh --update

# 查看状态
./deployment/scripts/deploy.sh --status
```

### 📖 用户使用
```bash
# 查看文档
cat README.md           # 项目介绍
cat docs/QUICK_START.md # 快速开始
```

---

## 🔧 增强功能

### 🆕 **Git自动拉取部署**

新的部署脚本支持从GitHub自动拉取最新代码：

```bash
# 自动更新代码并部署
./deployment/scripts/deploy.sh --update

# 检查代码版本
./deployment/scripts/deploy.sh --status
```

**功能特点**：
- ✅ 自动检测代码更新
- ✅ 智能暂存本地修改
- ✅ 显示版本更新信息
- ✅ 保持原有部署方式

---

## ✅ 优势特点

### 🎯 **清晰分离**
- 源码与配置分离，职责明确
- 便于版本控制和协作开发
- 易于理解和维护

### 🚀 **部署便利** 
- 保持原有Docker一键部署
- 增加Git自动拉取功能
- 支持多种部署模式

### 📚 **文档精简**
- 移除重复和无用文档
- 保留核心使用指南
- 结构清晰易懂

### 🔧 **易于扩展**
- 模块化目录结构
- 标准化配置管理
- 便于添加新功能

---

## 🛠️ 快速开始

```bash
# 1. 克隆项目
git clone https://github.com/wangheng19901021/search-assistant.git
cd search-assistant

# 2. 一键部署
./deployment/scripts/deploy.sh

# 3. 更新部署
./deployment/scripts/deploy.sh --update

# 4. 查看状态
./deployment/scripts/deploy.sh --status
```

**访问地址**：
- 🌐 前端应用: http://localhost
- 📡 后端API: http://localhost:8080

现在您可以清楚地了解每个文件和目录的作用，轻松进行开发和部署！