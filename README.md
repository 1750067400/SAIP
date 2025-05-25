# 产业园促进会会员管理系统

## 项目概述
这是一个产业园促进会的会员管理系统，包含会员管理、预约服务和数据统计功能。

## 项目结构
```
├── backend/                 # 后端代码
│   ├── src/main/java/
│   │   └── com/saip/
│   │       ├── entity/      # 实体类
│   │       ├── enums/       # 枚举类
│   │       ├── service/     # 服务层
│   │       ├── controller/  # 控制层
│   │       └── common/      # 通用类
│   └── src/main/resources/
│       └── schema.sql       # 数据库建表语句
└── frontend/                # 前端页面
    ├── index.html          # 会员管理页面
    ├── dashboard.html      # 数据统计仪表盘
    └── appointment.html    # 预约服务页面
```

## 功能特性
1. **会员管理**：支持会员的增删改查，包含普通会员、理事、常务理事、副会长、会长等级别
2. **预约服务**：支持咨询服务、培训服务、交流活动等预约
3. **数据统计**：提供会员类型分布、产业园入住率、访客趋势等统计图表

## 当前状态
- 后端代码已完成基本功能实现，使用内存存储数据
- 前端页面已完成UI设计和基本交互
- 由于Spring依赖问题，当前版本移除了Spring框架，使用纯Java实现

## 运行方式
1. 前端：直接在浏览器中打开HTML文件
2. 后端：运行SaipApplication.java的main方法进行测试

## 技术栈
- 后端：Java 11, MyBatis (配置), MySQL (建表语句)
- 前端：HTML5, Tailwind CSS, ECharts, JavaScript

## 注意事项
- 当前版本使用内存存储，重启后数据会丢失
- 如需完整的Spring Boot功能，需要正确配置Maven依赖
- 前端页面中的API调用目前会失败，因为后端没有启动Web服务器 