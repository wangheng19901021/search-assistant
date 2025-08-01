# 搜索助手项目部署和使用指南

## 项目概述
这是一个基于Spring Boot和微信小程序的物品查找助手，帮助用户管理和查找个人物品。

## 技术栈
- **后端**: Spring Boot 3.1.5, MyBatis Plus 3.5.8, MySQL 8.0
- **前端**: HTML5, CSS3, JavaScript (ES6+)
- **数据库**: MySQL 8.0

## 部署步骤

### 1. 环境准备
- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Node.js (可选，用于前端开发)

### 2. 数据库初始化
```sql
-- 1. 创建数据库
source backend/src/main/resources/sql/schema.sql

-- 2. 初始化测试数据
source init_test_data.sql
```

### 3. 后端启动
```bash
cd backend
mvn clean package
java -jar target/search-assistant-1.0.0.jar
```

或者在IDE中直接运行 `SearchAssistantApplication.java`

### 4. 前端部署
将 `frontend/index.html` 部署到任何Web服务器即可，或者直接用浏览器打开。

## 功能特性

### ✅ 已实现功能
1. **物品管理**
   - 添加物品（名称、分类、位置、描述）
   - 查看物品列表
   - 搜索物品（支持名称、分类、位置搜索）
   - 删除物品

2. **分类管理** 🆕
   - 预置8个系统分类（电子设备、书籍文具、衣物配饰等）
   - 添加用户自定义分类
   - 查看分类列表（区分系统分类和自定义分类）
   - 删除用户自定义分类（检查关联物品）
   - 系统分类不允许删除或修改

3. **位置管理**
   - 添加位置（名称、图标）
   - 查看位置列表
   - 删除位置（检查关联物品）

4. **数据安全**
   - 用户数据隔离
   - 参数验证
   - 错误处理

### 🔧 技术改进
1. **MyBatis兼容性修复**
   - 修复Spring Boot 3.2.0兼容性问题
   - 降级到Spring Boot 3.1.5确保稳定性
   - 正确的动态SQL语法

2. **CORS跨域支持**
   - 完整的CORS配置
   - 支持所有HTTP方法
   - 允许携带凭据

3. **错误处理增强**
   - 前端空指针安全检查
   - 后端完整异常处理
   - 用户友好错误提示

4. **生产就绪特性**
   - 日志记录
   - 数据验证
   - 事务管理
   - 连接池配置

## API接口文档

### 物品相关
- `GET /api/item/list` - 获取物品列表
- `POST /api/item/add` - 添加物品
- `PUT /api/item/{id}` - 更新物品
- `DELETE /api/item/{id}` - 删除物品
- `GET /api/item/{id}` - 获取物品详情

### 位置相关
- `GET /api/location/list` - 获取位置列表
- `POST /api/location/add` - 添加位置
- `PUT /api/location/{id}` - 更新位置
- `DELETE /api/location/{id}` - 删除位置

### 分类相关
- `GET /api/category/list?userId={userId}` - 获取用户分类列表（包含系统分类）
- `POST /api/category/add` - 添加用户自定义分类
- `PUT /api/category/{id}` - 更新用户自定义分类
- `DELETE /api/category/{id}` - 删除用户自定义分类

## 测试说明

### 自动化测试
运行提供的测试脚本：
```bash
# 基本API测试
chmod +x test_api.sh
./test_api.sh

# 分类管理功能测试
chmod +x test_category_management.sh
./test_category_management.sh
```

### 手动测试清单
1. ✅ 打开前端页面
2. ✅ 查看物品列表（应显示测试数据）
3. ✅ 搜索功能（输入关键词）
4. ✅ **分类管理** 🆕
   - 点击"分类"标签页
   - 查看系统分类和自定义分类
   - 添加新的自定义分类
   - 尝试删除自定义分类
   - 尝试删除系统分类（应被阻止）
5. ✅ 添加新物品（可选择自定义分类）
6. ✅ 添加新位置
7. ✅ 删除物品
8. ✅ 删除位置（测试关联检查）
9. ✅ 错误处理（网络断开等情况）

## 配置说明

### 数据库配置
修改 `backend/src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/search_assistant
    username: your_username
    password: your_password
```

### 微信小程序配置
```yaml
wechat:
  app-id: your_wechat_app_id
  app-secret: your_wechat_app_secret
```

## 生产环境优化建议

1. **安全性**
   - 使用HTTPS
   - 实现真实的用户认证
   - 添加API限流

2. **性能优化**
   - 数据库索引优化
   - 分页查询优化
   - 缓存机制

3. **监控运维**
   - 日志文件管理
   - 应用监控
   - 数据库备份

4. **扩展功能**
   - 图片上传
   - 二维码生成
   - 批量导入导出

## 故障排除

### 常见问题
1. **启动失败**: 检查JDK版本和MySQL连接
2. **CORS错误**: 确认后端CORS配置正确
3. **数据库连接失败**: 检查数据库服务和配置
4. **MyBatis错误**: 确认SQL语法和映射正确

### 日志查看
- 应用日志: `logs/application.log`
- 错误日志: `logs/error.log`

## 联系支持
如有问题，请检查日志文件或联系开发团队。