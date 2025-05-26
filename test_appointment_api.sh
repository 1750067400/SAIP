#!/bin/bash

echo "测试预约管理API..."

# 1. 添加预约
echo -e "\n1. 添加预约"
curl -X POST -H "Content-Type: application/json" -d '{
    "memberName": "测试会员1",
    "serviceType": "CONSULTING",
    "appointmentDate": "2024-03-20",
    "appointmentTime": "14:30",
    "remarks": "测试预约1"
}' http://localhost:8080/api/appointments | cat

# 2. 获取所有预约
echo -e "\n\n2. 获取所有预约"
curl -X GET http://localhost:8080/api/appointments | cat

# 3. 获取单个预约
echo -e "\n\n3. 获取单个预约"
curl -X GET http://localhost:8080/api/appointments/1 | cat

# 4. 更新预约
echo -e "\n\n4. 更新预约"
curl -X PUT -H "Content-Type: application/json" -d '{
    "memberName": "测试会员1",
    "serviceType": "TRAINING",
    "appointmentDate": "2024-03-21",
    "appointmentTime": "15:30",
    "remarks": "测试预约1-更新",
    "status": "CONFIRMED"
}' http://localhost:8080/api/appointments/1 | cat

# 5. 删除预约
echo -e "\n\n5. 删除预约"
curl -X DELETE http://localhost:8080/api/appointments/1 | cat 