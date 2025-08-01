#!/bin/bash

# 寻物助手应用启动脚本
echo "🚀 启动寻物助手应用..."

# 检查是否在项目根目录
if [ ! -d "backend" ]; then
    echo "❌ 请在项目根目录运行此脚本"
    exit 1
fi

# 检查Java环境
if ! command -v java &> /dev/null; then
    echo "❌ 未检测到Java环境"
    echo ""
    echo "请安装Java 17或更高版本："
    echo "Ubuntu/Debian: sudo apt update && sudo apt install openjdk-17-jdk"
    echo "CentOS/RHEL: sudo yum install java-17-openjdk-devel"
    echo "或从官网下载: https://adoptium.net/"
    echo ""
    exit 1
fi

# 检查Java版本
JAVA_VERSION=$(java -version 2>&1 | head -n1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ] 2>/dev/null; then
    echo "⚠️  检测到Java版本较低，建议使用Java 17或更高版本"
    echo "当前版本: $(java -version 2>&1 | head -n1)"
    echo ""
fi

# 检查MySQL连接（可选）
echo "💡 提示：确保MySQL 8.0已启动并可连接"
echo "   数据库: search_assistant"
echo "   用户: root (或已配置的用户)"
echo ""

# 进入backend目录
cd backend

# 检查Maven Wrapper
if [ ! -f "./mvnw" ]; then
    echo "❌ 未找到Maven Wrapper，正在尝试修复..."
    # 这里可以添加自动修复逻辑
    exit 1
fi

# 启动应用
echo "📦 启动Spring Boot应用..."
echo "应用将在 http://localhost:8080 运行"
echo "前端页面: http://localhost:8080/index.html"
echo ""
echo "🎯 如果这是首次运行，应用会："
echo "   1. 自动下载Maven依赖"
echo "   2. 编译项目"
echo "   3. 创建数据库表"
echo "   4. 启动服务"
echo ""
echo "⏳ 首次启动可能需要几分钟时间..."
echo "按 Ctrl+C 停止应用"
echo ""

# 使用Maven Wrapper启动，添加更多启动参数
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx512m -Dfile.encoding=UTF-8"