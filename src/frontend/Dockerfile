# 前端Dockerfile - 多阶段构建
FROM nginx:alpine

# 创建应用目录
WORKDIR /usr/share/nginx/html

# 删除默认的nginx内容
RUN rm -rf /usr/share/nginx/html/*

# 复制前端文件
COPY . .

# 复制自定义nginx配置
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 创建日志目录
RUN mkdir -p /var/log/nginx

# 设置权限
RUN chmod -R 755 /usr/share/nginx/html

# 暴露端口
EXPOSE 80

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost/ || exit 1

# 启动nginx
CMD ["nginx", "-g", "daemon off;"]