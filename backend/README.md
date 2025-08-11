# 产业园促进会管理系统 - 后端API

## 项目概述
这是产业园促进会管理系统的后端API，基于Spring Boot框架开发，提供完整的RESTful API服务。

## 技术栈
- **Java 17**
- **Spring Boot 3.3.3**
- **Spring Data JPA**
- **MySQL 8.0**
- **Maven**

## 项目结构
```
backend/
├── src/main/java/com/saip/
│   ├── entity/          # 实体类
│   ├── enums/           # 枚举类
│   ├── repository/       # 数据访问层
│   ├── service/          # 业务逻辑层
│   ├── controller/       # 控制器层
│   ├── config/           # 配置类
│   └── common/           # 通用类
├── src/main/resources/
│   ├── application.yml   # 应用配置
│   └── schema.sql        # 数据库建表语句
└── pom.xml              # Maven配置
```

## API接口文档

### 1. 认证相关接口

#### 用户登录
- **URL**: `POST /api/auth/login`
- **请求体**:
```json
{
    "username": "admin",
    "password": "123456"
}
```
- **响应**:
```json
{
    "code": 200,
    "message": "操作成功",
    "data": {
        "user": {...},
        "token": "mock-jwt-token-1"
    }
}
```

#### 用户注册
- **URL**: `POST /api/auth/register`
- **请求体**:
```json
{
    "username": "newuser",
    "password": "123456",
    "name": "新用户",
    "email": "newuser@example.com",
    "role": "USER"
}
```

### 2. 用户管理接口

#### 获取所有用户
- **URL**: `GET /api/users`

#### 获取用户详情
- **URL**: `GET /api/users/{id}`

#### 创建用户
- **URL**: `POST /api/users`

#### 更新用户
- **URL**: `PUT /api/users/{id}`

#### 删除用户
- **URL**: `DELETE /api/users/{id}`

#### 启用/禁用用户
- **URL**: `PUT /api/users/{id}/toggle-status`

### 3. 活动管理接口

#### 获取所有活动
- **URL**: `GET /api/events`

#### 获取活动详情
- **URL**: `GET /api/events/{id}`

#### 创建活动
- **URL**: `POST /api/events`

#### 更新活动
- **URL**: `PUT /api/events/{id}`

#### 删除活动
- **URL**: `DELETE /api/events/{id}`

#### 获取已发布活动
- **URL**: `GET /api/events/published`

#### 获取即将开始活动
- **URL**: `GET /api/events/upcoming`

### 4. 项目管理接口

#### 获取所有项目
- **URL**: `GET /api/projects`

#### 获取项目详情
- **URL**: `GET /api/projects/{id}`

#### 创建项目
- **URL**: `POST /api/projects`

#### 更新项目
- **URL**: `PUT /api/projects/{id}`

#### 删除项目
- **URL**: `DELETE /api/projects/{id}`

#### 根据类型获取项目
- **URL**: `GET /api/projects/type/{projectType}`

#### 根据状态获取项目
- **URL**: `GET /api/projects/status/{status}`

### 5. 文档管理接口

#### 获取所有文档
- **URL**: `GET /api/documents`

#### 获取文档详情
- **URL**: `GET /api/documents/{id}`

#### 上传文档
- **URL**: `POST /api/documents/upload`
- **Content-Type**: `multipart/form-data`

#### 更新文档
- **URL**: `PUT /api/documents/{id}`

#### 删除文档
- **URL**: `DELETE /api/documents/{id}`

#### 获取公开文档
- **URL**: `GET /api/documents/public`

#### 搜索文档
- **URL**: `GET /api/documents/search?keyword=关键词`

### 6. 统计报表接口

#### 获取仪表盘统计
- **URL**: `GET /api/statistics/dashboard`

#### 获取会员增长趋势
- **URL**: `GET /api/statistics/members/growth`

#### 获取会员行业分布
- **URL**: `GET /api/statistics/members/industry`

#### 获取活动参与统计
- **URL**: `GET /api/statistics/events/participation`

#### 获取项目进度统计
- **URL**: `GET /api/statistics/projects/progress`

#### 获取财务收入统计
- **URL**: `GET /api/statistics/finance/revenue`

#### 获取自定义报表
- **URL**: `GET /api/statistics/custom?reportType=member&timeRange=30`

## 数据库配置

### 数据库连接配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/saip?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 数据库表结构
系统包含以下主要数据表：
- `users` - 用户表
- `members` - 会员表
- `events` - 活动表
- `projects` - 项目表
- `documents` - 文档表
- `payments` - 支付记录表
- `appointments` - 预约服务表
- `companies` - 企业表
- `industrial_parks` - 产业园表
- `system_logs` - 系统日志表

## 运行说明

### 1. 环境要求
- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 2. 数据库准备
1. 创建数据库：`CREATE DATABASE saip CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`
2. 修改 `application.yml` 中的数据库连接信息
3. 启动应用，系统会自动执行 `schema.sql` 创建表结构

### 3. 启动应用
```bash
# 编译项目
mvn clean compile

# 运行应用
mvn spring-boot:run
```

### 4. 访问API
- 应用启动后，API服务运行在 `http://localhost:8080`
- API基础路径：`http://localhost:8080/api`
- 可以使用Postman或其他API测试工具进行测试

## 默认用户
系统初始化时会创建以下默认用户：
- **超级管理员**: admin / 123456
- **管理员**: manager / 123456
- **普通用户**: user / 123456

3
## 注意事项
1. 当前版本使用简单的密码编码器，生产环境建议使用更安全的加密方式
2. 文件上传功能默认保存在 `uploads/documents/` 目录下
3. 所有API都支持CORS跨域访问
4. 系统日志会记录用户操作，便于审计和问题排查 