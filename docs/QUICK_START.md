# 🚀 寻物助手 - 快速启动指南

## 环境要求

### 必需环境
- **Java 17+** (推荐OpenJDK 17)
- **MySQL 8.0+**
- **现代浏览器** (Chrome, Firefox, Safari, Edge)

### 安装Java环境

#### Ubuntu/Debian
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

#### CentOS/RHEL/Rocky Linux
```bash
sudo yum install java-17-openjdk-devel
# 或使用 dnf
sudo dnf install java-17-openjdk-devel
```

#### Windows
从 [Adoptium](https://adoptium.net/) 下载并安装OpenJDK 17

#### macOS
```bash
# 使用 Homebrew
brew install openjdk@17
```

### 验证Java安装
```bash
java -version
# 应显示 Java 17 或更高版本
```

## 数据库设置

### MySQL配置
1. 安装MySQL 8.0
2. 创建数据库：
```sql
CREATE DATABASE search_assistant CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. 创建用户（可选）：
```sql
CREATE USER 'findthing'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON search_assistant.* TO 'findthing'@'localhost';
FLUSH PRIVILEGES;
```

### 配置文件
编辑 `backend/src/main/resources/application.yml`，确保数据库连接信息正确：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/search_assistant
    username: root  # 或您的用户名
    password: your_password  # 您的密码
```

## 启动应用

### 方法1: 使用启动脚本（推荐）
```bash
cd /path/to/Search\ Assistant
chmod +x start.sh
./start.sh
```

### 方法2: 手动启动
```bash
cd /path/to/Search\ Assistant/backend
./mvnw spring-boot:run
```

## 访问应用

启动成功后，访问：
- **主应用**: http://localhost:8080/index.html
- **API文档**: http://localhost:8080/doc.html (如果启用了Swagger)

## 首次运行

第一次启动时：
1. ⏳ **下载依赖** - Maven会下载所需的Java库（2-5分钟）
2. 🔨 **编译项目** - 编译Spring Boot应用
3. 🗄️ **初始化数据库** - 自动创建数据表
4. 🚀 **启动服务** - 应用准备就绪

看到以下信息表示启动成功：
```
Started SearchAssistantApplication in X.XXX seconds
```

## 故障排除

### 常见问题

#### 1. "java: command not found"
- 安装Java 17+，确保已添加到PATH环境变量

#### 2. "Access denied for user 'root'@'localhost'"
- 检查MySQL用户名和密码
- 确保MySQL服务正在运行

#### 3. "Port 8080 was already in use"
- 停止占用8080端口的程序
- 或修改 `application.yml` 中的 `server.port`

#### 4. "mvnw: Permission denied"
- 运行: `chmod +x backend/mvnw`

#### 5. Maven依赖下载失败
- 检查网络连接
- 配置Maven镜像（如阿里云镜像）

### 日志查看
应用启动后会在控制台显示详细日志，出现问题时请查看错误信息。

## 开发模式

### 热重载
在开发模式下，修改Java代码后会自动重启应用：
```bash
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.devtools.restart.enabled=true"
```

### 调试模式
启动调试模式（端口5005）：
```bash
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

## 生产部署

### 构建JAR包
```bash
./mvnw clean package -DskipTests
```
构建完成后，JAR包位于 `target/` 目录。

### 运行JAR包
```bash
java -jar target/search-assistant-2.0.0.jar
```

## 技术支持

如遇到问题：
1. 检查Java和MySQL版本
2. 查看控制台错误信息
3. 确认防火墙和端口设置
4. 检查数据库连接权限

---

💡 **提示**: 首次启动需要联网下载依赖，请确保网络连接正常。