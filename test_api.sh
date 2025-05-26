#!/bin/bash

echo "开始测试API接口..."

# 测试会员管理接口
echo -e "\n1. 测试会员管理接口"
echo "1.1 获取所有会员"
curl -X GET http://localhost:8080/api/members | cat
echo -e "\n1.2 添加新会员"
curl -X POST -H "Content-Type: application/json" -d '{"name":"测试会员","company":"测试公司","position":"测试职位","phone":"13800138000","email":"test@example.com"}' http://localhost:8080/api/members | cat

# 测试预约管理接口
echo -e "\n\n2. 测试预约管理接口"
echo "2.1 获取所有预约"
curl -X GET http://localhost:8080/api/appointments | cat
echo -e "\n2.2 添加新预约"
curl -X POST -H "Content-Type: application/json" -d '{"memberName":"测试会员","serviceType":"CONSULTING","appointmentDate":"2024-03-20","appointmentTime":"14:30","remarks":"测试预约"}' http://localhost:8080/api/appointments | cat

# 测试统计接口
echo -e "\n\n3. 测试统计接口"
echo "3.1 获取会员统计"
curl -X GET http://localhost:8080/api/statistics/members | cat
echo -e "\n3.2 获取预约统计"
curl -X GET http://localhost:8080/api/statistics/appointments | cat 