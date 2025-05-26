#!/bin/bash

echo "测试统计接口..."

# 1. 获取会员统计
echo -e "\n1. 获取会员统计"
curl -X GET http://localhost:8080/api/statistics/members | cat

# 2. 获取预约统计
echo -e "\n\n2. 获取预约统计"
curl -X GET http://localhost:8080/api/statistics/appointments | cat

# 3. 获取仪表盘数据
echo -e "\n\n3. 获取仪表盘数据"
curl -X GET http://localhost:8080/api/statistics/dashboard | cat 