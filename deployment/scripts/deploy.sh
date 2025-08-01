#!/bin/bash

# 寻物助手Docker部署脚本
# 支持多种部署方式

set -e

# 切换到deployment目录
cd "$(dirname "$0")/.." || exit 1

echo "🚀 寻物助手 Docker 部署工具"
echo "================================"

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 显示帮助信息
show_help() {
    echo "使用方法："
    echo "  ./deploy.sh [选项]"
    echo ""
    echo "选项："
    echo "  --single, -s      单容器部署（一个容器包含所有服务）"
    echo "  --multi, -m       多容器部署（默认，服务分离）"
    echo "  --dev, -d         开发模式部署"
    echo "  --prod, -p        生产模式部署"
    echo "  --stop            停止所有服务"
    echo "  --restart         重启所有服务"
    echo "  --logs            查看日志"
    echo "  --clean           清理所有数据和镜像"
    echo "  --help, -h        显示帮助信息"
    echo ""
    echo "示例："
    echo "  ./scripts/deploy.sh              # 默认多容器部署"
    echo "  ./scripts/deploy.sh --single     # 单容器部署"
    echo "  ./scripts/deploy.sh --prod       # 生产环境部署"
    echo "  ./scripts/deploy.sh --stop       # 停止服务"
    echo "  ./scripts/deploy.sh --logs       # 查看日志"
}

# 检查Docker环境
check_docker() {
    if ! command -v docker &> /dev/null; then
        echo -e "${RED}❌ Docker未安装，请先安装Docker${NC}"
        echo "安装指南: https://docs.docker.com/get-docker/"
        exit 1
    fi

    if ! command -v docker-compose &> /dev/null && ! docker compose version &> /dev/null; then
        echo -e "${RED}❌ Docker Compose未安装，请先安装Docker Compose${NC}"
        echo "安装指南: https://docs.docker.com/compose/install/"
        exit 1
    fi

    echo -e "${GREEN}✅ Docker环境检查通过${NC}"
}

# 检查端口占用
check_ports() {
    local ports=("80" "8080" "3306")
    local occupied_ports=()

    for port in "${ports[@]}"; do
        if lsof -Pi :$port -sTCP:LISTEN -t >/dev/null 2>&1; then
            occupied_ports+=($port)
        fi
    done

    if [ ${#occupied_ports[@]} -gt 0 ]; then
        echo -e "${YELLOW}⚠️  以下端口被占用: ${occupied_ports[*]}${NC}"
        echo -e "${YELLOW}请停止占用这些端口的服务，或修改配置文件中的端口设置${NC}"
        read -p "是否继续部署？(y/N): " -n 1 -r
        echo
        if [[ ! $REPLY =~ ^[Yy]$ ]]; then
            exit 1
        fi
    fi
}

# 创建必要的目录和文件
prepare_environment() {
    echo -e "${BLUE}📁 准备部署环境...${NC}"
    
    # 创建日志目录
    mkdir -p logs/{nginx,backend,mysql}
    
    # 创建上传目录
    mkdir -p uploads
    
    # 设置权限
    chmod -R 755 uploads logs
    
    echo -e "${GREEN}✅ 环境准备完成${NC}"
}

# 单容器部署
deploy_single() {
    echo -e "${BLUE}🐳 单容器部署模式${NC}"
    echo "所有服务将运行在一个容器中..."
    
    prepare_environment
    
    echo "构建并启动容器..."
    docker-compose -f docker-compose.single.yml up --build -d
    
    show_deployment_info "single"
}

# 多容器部署
deploy_multi() {
    echo -e "${BLUE}🐳 多容器部署模式${NC}"
    echo "前端、后端、数据库将分别运行在独立容器中..."
    
    prepare_environment
    
    echo "构建并启动所有服务..."
    docker-compose -f docker-compose.yml up --build -d
    
    show_deployment_info "multi"
}

# 开发模式部署
deploy_dev() {
    echo -e "${BLUE}🔧 开发模式部署${NC}"
    
    # 复制开发配置
    cp docker-compose.yml docker-compose.dev.yml
    
    # 修改开发配置（可以添加更多开发特定设置）
    sed -i 's/restart: unless-stopped/restart: "no"/g' docker-compose.dev.yml
    
    prepare_environment
    docker-compose -f docker-compose.dev.yml up --build -d
    
    show_deployment_info "dev"
}

# 生产模式部署
deploy_prod() {
    echo -e "${BLUE}🏭 生产模式部署${NC}"
    
    # 检查生产环境配置
    if [ ! -f ".env.prod" ]; then
        echo -e "${YELLOW}⚠️  未找到生产环境配置文件 .env.prod${NC}"
        echo "创建默认配置..."
        cat > .env.prod << EOF
# 生产环境配置
MYSQL_ROOT_PASSWORD=your_secure_root_password_here
MYSQL_PASSWORD=your_secure_password_here
COMPOSE_PROJECT_NAME=search-assistant-prod
EOF
        echo -e "${YELLOW}请编辑 .env.prod 文件，设置安全的密码${NC}"
        read -p "按回车键继续..."
    fi
    
    prepare_environment
    docker-compose --env-file .env.prod -f docker-compose.yml up --build -d
    
    show_deployment_info "prod"
}

# 显示部署信息
show_deployment_info() {
    local mode=$1
    
    echo ""
    echo -e "${GREEN}🎉 部署完成！${NC}"
    echo "================================"
    
    # 等待服务启动
    echo -e "${YELLOW}⏳ 等待服务启动...${NC}"
    sleep 10
    
    echo "访问地址："
    echo -e "  🌐 前端页面: ${BLUE}http://localhost${NC}"
    echo -e "  📡 后端API:  ${BLUE}http://localhost:8080${NC}"
    
    if [ "$mode" != "single" ]; then
        echo -e "  🗄️  数据库:   ${BLUE}localhost:3306${NC}"
    fi
    
    echo ""
    echo "默认账号信息："
    echo "  数据库用户: findthing"
    echo "  数据库密码: findthing123"
    echo "  数据库名称: search_assistant"
    echo ""
    echo "常用命令："
    echo "  查看日志: ./scripts/deploy.sh --logs"
    echo "  停止服务: ./scripts/deploy.sh --stop"
    echo "  重启服务: ./scripts/deploy.sh --restart"
    echo ""
    
    # 健康检查
    check_health
}

# 健康检查
check_health() {
    echo -e "${BLUE}🔍 健康检查...${NC}"
    
    local attempts=0
    local max_attempts=30
    
    while [ $attempts -lt $max_attempts ]; do
        if curl -f -s http://localhost/health > /dev/null 2>&1; then
            echo -e "${GREEN}✅ 前端服务正常${NC}"
            break
        fi
        ((attempts++))
        sleep 2
    done
    
    attempts=0
    while [ $attempts -lt $max_attempts ]; do
        if curl -f -s http://localhost:8080/actuator/health > /dev/null 2>&1; then
            echo -e "${GREEN}✅ 后端服务正常${NC}"
            break
        fi
        ((attempts++))
        sleep 2
    done
    
    if [ $attempts -eq $max_attempts ]; then
        echo -e "${RED}⚠️  某些服务可能还在启动中，请稍后检查${NC}"
    fi
}

# 停止服务
stop_services() {
    echo -e "${YELLOW}🛑 停止所有服务...${NC}"
    
    # 尝试停止所有可能的compose文件
    docker-compose -f docker-compose.yml down 2>/dev/null || true
    docker-compose -f docker-compose.single.yml down 2>/dev/null || true
    docker-compose -f docker-compose.dev.yml down 2>/dev/null || true
    
    echo -e "${GREEN}✅ 服务已停止${NC}"
}

# 重启服务
restart_services() {
    echo -e "${BLUE}🔄 重启服务...${NC}"
    stop_services
    sleep 3
    deploy_multi  # 默认使用多容器模式重启
}

# 查看日志
show_logs() {
    echo -e "${BLUE}📋 查看服务日志...${NC}"
    echo "按 Ctrl+C 退出日志查看"
    sleep 2
    
    if docker-compose -f docker-compose.yml ps | grep -q "search-assistant"; then
        docker-compose -f docker-compose.yml logs -f
    elif docker ps | grep -q "search-assistant-all-in-one"; then
        docker logs -f search-assistant-all-in-one
    else
        echo -e "${RED}❌ 未找到运行中的服务${NC}"
    fi
}

# 清理环境
clean_environment() {
    echo -e "${RED}🧹 清理环境...${NC}"
    echo -e "${YELLOW}⚠️  这将删除所有容器、镜像和数据！${NC}"
    read -p "确定要继续吗？(y/N): " -n 1 -r
    echo
    
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        # 停止并删除容器
        docker-compose -f docker-compose.yml down -v --rmi all 2>/dev/null || true
        docker-compose -f docker-compose.single.yml down -v --rmi all 2>/dev/null || true
        
        # 删除相关镜像
        docker images | grep search-assistant | awk '{print $3}' | xargs -r docker rmi -f
        
        # 清理数据目录
        sudo rm -rf logs uploads
        
        echo -e "${GREEN}✅ 清理完成${NC}"
    else
        echo "取消清理"
    fi
}

# 主函数
main() {
    case "${1:-multi}" in
        --single|-s)
            check_docker
            check_ports
            deploy_single
            ;;
        --multi|-m|"")
            check_docker
            check_ports
            deploy_multi
            ;;
        --dev|-d)
            check_docker
            check_ports
            deploy_dev
            ;;
        --prod|-p)
            check_docker
            check_ports
            deploy_prod
            ;;
        --stop)
            stop_services
            ;;
        --restart)
            restart_services
            ;;
        --logs)
            show_logs
            ;;
        --clean)
            clean_environment
            ;;
        --help|-h)
            show_help
            ;;
        *)
            echo -e "${RED}❌ 未知选项: $1${NC}"
            show_help
            exit 1
            ;;
    esac
}

# 脚本入口
main "$@"