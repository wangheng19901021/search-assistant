#!/bin/bash

# 项目代码统计脚本

echo "📊 寻物助手项目统计"
echo "=================="

echo ""
echo "📁 目录结构:"
find . -type d -not -path "./.git*" -not -path "./target*" -not -path "./logs*" | sort

echo ""
echo "📄 文件统计:"
echo "总文件数: $(find . -type f -not -path "./.git*" -not -path "./target*" -not -path "./logs*" | wc -l)"
echo "代码文件: $(find . -name "*.java" -o -name "*.html" -o -name "*.js" -o -name "*.css" -o -name "*.yml" -o -name "*.xml" | wc -l)"
echo "文档文件: $(find . -name "*.md" | wc -l)"
echo "配置文件: $(find . -name "*.yml" -o -name "*.xml" -o -name "*.conf" -o -name "*.cnf" | wc -l)"

echo ""
echo "💻 代码行数统计:"
echo "Java代码: $(find . -name "*.java" -exec wc -l {} + 2>/dev/null | tail -1 | awk '{print $1}' || echo "0") 行"
echo "前端代码: $(find . -name "*.html" -o -name "*.js" -o -name "*.css" -exec wc -l {} + 2>/dev/null | tail -1 | awk '{print $1}' || echo "0") 行"
echo "配置文件: $(find . -name "*.yml" -o -name "*.xml" -o -name "*.sql" -exec wc -l {} + 2>/dev/null | tail -1 | awk '{print $1}' || echo "0") 行"

echo ""
echo "🐳 Docker配置:"
echo "Dockerfile数量: $(find . -name "Dockerfile*" | wc -l)"
echo "Compose文件: $(find . -name "docker-compose*.yml" | wc -l)"

echo ""
echo "📚 文档完整性:"
echo "README.md: $([ -f README.md ] && echo "✅" || echo "❌")"
echo "部署文档: $([ -f docs/SERVER_DEPLOYMENT_GUIDE.md ] && echo "✅" || echo "❌")"
echo "Docker文档: $([ -f docs/DOCKER_DEPLOYMENT.md ] && echo "✅" || echo "❌")"
echo "目录说明: $([ -f DIRECTORY_STRUCTURE.md ] && echo "✅" || echo "❌")"

echo ""
echo "🔧 项目配置:"
echo "Git配置: $([ -f .gitignore ] && echo "✅" || echo "❌")"
echo "部署脚本: $([ -x deployment/scripts/deploy.sh ] && echo "✅" || echo "❌")"
echo "Maven配置: $([ -f src/backend/pom.xml ] && echo "✅" || echo "❌")"

echo ""
echo "📊 项目规模评估:"
TOTAL_LINES=$(find . -name "*.java" -o -name "*.html" -o -name "*.js" -o -name "*.css" -o -name "*.yml" -o -name "*.xml" -o -name "*.sql" -exec wc -l {} + 2>/dev/null | tail -1 | awk '{print $1}' || echo "0")
echo "总代码行数: $TOTAL_LINES 行"

if [ $TOTAL_LINES -lt 1000 ]; then
    echo "项目规模: 小型项目 📱"
elif [ $TOTAL_LINES -lt 5000 ]; then
    echo "项目规模: 中型项目 💻"
else
    echo "项目规模: 大型项目 🏢"
fi