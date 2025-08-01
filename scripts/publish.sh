#!/bin/bash

# 寻物助手项目发布自动化脚本
# 用法: ./publish.sh [版本号] [发布说明]

set -e

VERSION=${1:-"auto"}
MESSAGE=${2:-"版本更新"}

echo "🚀 寻物助手项目发布工具"
echo "========================="

# 检查Git状态
if [ -n "$(git status --porcelain)" ]; then
    echo "❌ 工作区有未提交的更改，请先提交"
    git status
    exit 1
fi

# 自动生成版本号
if [ "$VERSION" = "auto" ]; then
    LAST_TAG=$(git describe --tags --abbrev=0 2>/dev/null || echo "v0.0.0")
    echo "📋 上个版本: $LAST_TAG"
    
    # 提取版本号并递增
    VERSION_NUM=$(echo $LAST_TAG | sed 's/v//' | awk -F. '{print $1"."$2"."($3+1)}')
    VERSION="v$VERSION_NUM"
fi

echo "🏷️  新版本: $VERSION"
echo "📝 发布说明: $MESSAGE"

read -p "确认发布？(y/N): " -n 1 -r
echo
if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    echo "取消发布"
    exit 1
fi

# 更新CHANGELOG
echo "📝 更新CHANGELOG..."
DATE=$(date +"%Y-%m-%d")
cat > temp_changelog.md << EOF
# 更新日志

## [$VERSION] - $DATE

### 更新内容
- $MESSAGE

$(tail -n +3 docs/CHANGELOG.md 2>/dev/null || echo "")
EOF

mv temp_changelog.md docs/CHANGELOG.md

# 提交更改
echo "💾 提交更改..."
git add docs/CHANGELOG.md
git commit -m "📝 更新CHANGELOG for $VERSION"

# 创建标签
echo "🏷️  创建版本标签..."
git tag -a $VERSION -m "$VERSION: $MESSAGE"

# 推送到远程仓库
echo "📤 推送到远程仓库..."
git push origin main
git push origin $VERSION

# 如果配置了多个远程仓库
if git remote | grep -q "gitee"; then
    echo "📤 推送到Gitee..."
    git push gitee main
    git push gitee $VERSION
fi

echo ""
echo "🎉 发布完成！"
echo "版本: $VERSION"
echo "下一步:"
echo "1. 访问 GitHub/Gitee 创建 Release"
echo "2. 更新部署服务器"
echo "3. 通知用户更新"