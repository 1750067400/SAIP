#!/bin/bash

echo "启动产业园促进会管理系统后端..."

# 检查Java版本
java_version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$java_version" -lt "17" ]; then
    echo "错误: 需要Java 17或更高版本"
    echo "当前Java版本: $(java -version 2>&1 | head -n 1)"
    exit 1
fi

# 检查Maven是否安装
if ! command -v mvn &> /dev/null; then
    echo "错误: 未找到Maven，请先安装Maven"
    exit 1
fi

# 清理并编译项目
echo "编译项目..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "编译失败，请检查错误信息"
    exit 1
fi

# 启动应用
echo "启动应用..."
mvn spring-boot:run

echo "应用已启动，访问地址: http://localhost:8080/api"
echo "测试API: http://localhost:8080/api/test/ping" 