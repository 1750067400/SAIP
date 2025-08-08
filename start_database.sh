#!/bin/bash

# =====================================================
# 产业园促进会管理系统 - 数据库启动脚本
# =====================================================

echo "🚀 启动产业园促进会管理系统数据库..."

# 检查MySQL服务状态
echo "📋 检查MySQL服务状态..."
if ! mysql.server status > /dev/null 2>&1; then
    echo "⚠️  MySQL服务未运行，正在启动..."
    mysql.server start
    sleep 3
else
    echo "✅ MySQL服务正在运行"
fi

# 检查数据库是否存在
echo "🔍 检查数据库是否存在..."
if mysql -u root -e "USE saip;" > /dev/null 2>&1; then
    echo "✅ 数据库 'saip' 已存在"
else
    echo "📝 数据库 'saip' 不存在，正在创建..."
    mysql -u root < init_database.sql
    echo "✅ 数据库创建完成"
fi

# 显示数据库信息
echo "📊 数据库信息："
mysql -u root -e "USE saip; SHOW TABLES;" 2>/dev/null || echo "❌ 无法连接到数据库"

echo "🎉 数据库启动完成！"
echo ""
echo "📝 使用说明："
echo "1. 启动后端服务: cd backend && mvn spring-boot:run"
echo "2. 访问前端页面: 打开 frontend/index.html"
echo "3. 默认管理员账号: admin / admin123"
echo ""
echo "🔗 API地址: http://localhost:8080/api"
echo "📁 前端页面: file://$(pwd)/frontend/index.html" 