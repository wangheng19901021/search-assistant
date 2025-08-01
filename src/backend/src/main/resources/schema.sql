-- schema.sql - 数据库表结构
-- 自动创建tb_前缀的表

-- 用户表
CREATE TABLE IF NOT EXISTS tb_user (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    openid VARCHAR(100) UNIQUE COMMENT '微信openid',
    nickname VARCHAR(100) COMMENT '用户昵称',
    avatar_url VARCHAR(500) COMMENT '头像URL',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '用户表';

-- 分类表
CREATE TABLE IF NOT EXISTS tb_category (
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    user_id BIGINT NULL COMMENT '用户ID，NULL表示系统分类',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统分类：0-用户自定义，1-系统预设',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '分类表';

-- 位置表
CREATE TABLE IF NOT EXISTS tb_location (
    location_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '位置ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    name VARCHAR(100) NOT NULL COMMENT '位置名称',
    icon VARCHAR(10) DEFAULT '📦' COMMENT '位置图标',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '位置表';

-- 物品表
CREATE TABLE IF NOT EXISTS tb_item (
    item_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '物品ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    name VARCHAR(100) NOT NULL COMMENT '物品名称',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    location_id BIGINT NOT NULL COMMENT '位置ID',
    description TEXT COMMENT '物品描述',
    image_url VARCHAR(500) COMMENT '物品图片URL',
    last_update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除'
) COMMENT '物品表';

-- 物品历史记录表
CREATE TABLE IF NOT EXISTS tb_item_history (
    history_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '历史记录ID',
    item_id BIGINT NOT NULL COMMENT '物品ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    action VARCHAR(100) NOT NULL COMMENT '操作描述',
    action_type VARCHAR(50) NOT NULL COMMENT '操作类型：create, update, delete, move',
    changes TEXT COMMENT '变更详情JSON格式',
    operator VARCHAR(100) DEFAULT '用户' COMMENT '操作者',
    timestamp DATETIME NOT NULL COMMENT '操作时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间'
) COMMENT '物品历史记录表';