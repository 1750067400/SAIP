#!/bin/bash

echo "启动SAIP应用..."

# 检查Maven是否安装
if ! command -v mvn &> /dev/null; then
    echo "错误: 未找到Maven，请先安装Maven"
    exit 1
fi

# 清理并编译项目
echo "编译项目..."
mvn clean package -DskipTests

# 检查编译是否成功
if [ $? -ne 0 ]; then
    echo "错误: 项目编译失败"
    exit 1
fi

# 运行应用
echo "启动应用..."
java -jar target/saip-backend-1.0.0.jar 