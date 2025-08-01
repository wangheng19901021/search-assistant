-- data.sql - 初始化数据
-- 插入测试数据和系统默认数据

-- 插入测试用户
INSERT IGNORE INTO tb_user (user_id, openid, nickname, avatar_url) VALUES 
(10001, 'test_openid_001', '测试用户', 'https://example.com/avatar.jpg');

-- 插入系统默认分类
INSERT IGNORE INTO tb_category (name, is_system, user_id) VALUES 
('电子产品', 1, NULL),
('书籍文具', 1, NULL),  
('服装配饰', 1, NULL),
('生活用品', 1, NULL),
('运动健身', 1, NULL),
('工具设备', 1, NULL),
('食品饮料', 1, NULL),
('其他物品', 1, NULL);

-- 插入默认位置（关联到测试用户）
INSERT IGNORE INTO tb_location (user_id, name, icon) VALUES 
(10001, '客厅', '🛋️'),
(10001, '卧室', '🛏️'),
(10001, '厨房', '🍳'),
(10001, '书房', '📚'),
(10001, '储物间', '📦'),
(10001, '车库', '🚗'),
(10001, '办公室', '💼'),
(10001, '其他位置', '📍');