# 产业园促进会管理系统

## 项目概述
这是一个完整的产业园促进会管理系统，包含前端页面和后端API服务。

## 项目结构
```
├── frontend/          # 前端页面
│   ├── index.html     # 会员管理主页
│   ├── dashboard.html # 数据统计仪表盘
│   ├── events.html    # 活动管理
│   ├── projects.html  # 项目管理
│   ├── documents.html # 文档管理
│   ├── permissions.html # 权限管理
│   ├── settings.html  # 系统设置
│   └── ...           # 其他页面
├── backend/           # 后端API服务
│   ├── src/main/java/com/saip/
│   │   ├── entity/    # 实体类
│   │   ├── controller/ # 控制器
│   │   ├── service/   # 服务层
│   │   └── repository/ # 数据访问层
│   └── src/main/resources/
│       ├── application.yml # 配置文件
│       └── schema.sql     # 数据库建表语句
└── README.md
```

## 功能特性

### 前端功能
- ✅ **会员管理** - 会员信息管理、分类统计
- ✅ **活动管理** - 活动发布、参与管理
- ✅ **项目管理** - 项目跟踪、进度管理
- ✅ **文档管理** - 文档上传、分类管理
- ✅ **权限管理** - 用户角色、权限控制
- ✅ **系统设置** - 基础配置、安全设置
- ✅ **数据统计** - 图表展示、报表生成
- ✅ **响应式设计** - 支持移动端访问

### 后端功能
- ✅ **RESTful API** - 完整的API接口
- ✅ **用户认证** - 登录注册、权限验证
- ✅ **数据持久化** - MySQL数据库存储
- ✅ **文件上传** - 文档上传和管理
- ✅ **统计报表** - 数据统计和分析
- ✅ **CORS支持** - 跨域访问支持

## 技术栈

### 前端
- **HTML5** - 页面结构
- **Tailwind CSS** - 样式框架
- **JavaScript** - 交互逻辑
- **ECharts** - 数据可视化
- **Font Awesome** - 图标库

### 后端
- **Java 17** - 编程语言
- **Spring Boot 3.3.3** - 框架
- **Spring Data JPA** - 数据访问
- **MySQL 8.0** - 数据库
- **Maven** - 项目管理

## 快速开始

### 1. 环境要求
- JDK 17+
- MySQL 8.0+
- Maven 3.6+
- 现代浏览器

### 2. 数据库准备
```sql
-- 创建数据库
CREATE DATABASE saip CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 配置数据库
编辑 `backend/src/main/resources/application.yml` 文件：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/saip?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 4. 启动后端服务

#### Linux/Mac
```bash
cd backend
chmod +x start.sh
./start.sh
```

#### Windows
```cmd
cd backend
start.bat
```

#### 手动启动
```bash
cd backend
mvn clean compile
mvn spring-boot:run
```

### 5. 访问前端页面
直接在浏览器中打开 `frontend/index.html` 文件

### 6. 测试API
访问测试接口：`http://localhost:8080/api/test/ping`

## 默认用户
- **超级管理员**: admin / 123456
- **管理员**: manager / 123456
- **普通用户**: user / 123456

## API文档

### 认证接口
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/register` - 用户注册

### 用户管理
- `GET /api/users` - 获取用户列表
- `POST /api/users` - 创建用户
- `PUT /api/users/{id}` - 更新用户

### 活动管理
- `GET /api/events` - 获取活动列表
- `POST /api/events` - 创建活动
- `PUT /api/events/{id}` - 更新活动

### 项目管理
- `GET /api/projects` - 获取项目列表
- `POST /api/projects` - 创建项目
- `PUT /api/projects/{id}` - 更新项目

### 文档管理
- `GET /api/documents` - 获取文档列表
- `POST /api/documents/upload` - 上传文档
- `DELETE /api/documents/{id}` - 删除文档

### 统计报表
- `GET /api/statistics/dashboard` - 仪表盘统计
- `GET /api/statistics/members/growth` - 会员增长趋势
- `GET /api/statistics/events/participation` - 活动参与统计

## 开发说明

### 前端开发
- 所有页面都使用统一的导航结构
- 使用Tailwind CSS进行样式设计
- 支持响应式布局
- 使用ECharts进行数据可视化

### 后端开发
- 使用Spring Boot框架
- JPA进行数据持久化
- 统一的API响应格式
- 完整的错误处理

## 注意事项
1. 确保MySQL服务正在运行
2. 检查数据库连接配置
3. 确保端口8080未被占用
4. 首次启动会自动创建数据库表结构

## 故障排除

### 常见问题
1. **数据库连接失败**
   - 检查MySQL服务是否启动
   - 验证数据库连接配置
   - 确认数据库用户权限

2. **编译错误**
   - 确保JDK版本为17或更高
   - 检查Maven是否正确安装
   - 清理并重新编译项目

3. **端口占用**
   - 修改 `application.yml` 中的端口配置
   - 或者停止占用8080端口的其他服务

## 许可证
本项目仅供学习和演示使用。 