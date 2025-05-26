#!/bin/bash

echo "测试会员管理API..."

# 1. 添加会员
echo -e "\n1. 添加会员"
curl -X POST -H "Content-Type: application/json" -d '{
    "name": "测试会员1",
    "company": "测试公司1",
    "position": "测试职位1",
    "phone": "13800138001",
    "email": "test1@example.com",
    "type": "COMPANY",
    "level": "NORMAL"
}' http://localhost:8080/api/members -v | cat

# 2. 获取所有会员
echo -e "\n\n2. 获取所有会员"
curl -X GET http://localhost:8080/api/members -v | cat

# 3. 获取单个会员
echo -e "\n\n3. 获取单个会员"
curl -X GET http://localhost:8080/api/members/1 -v | cat

# 4. 更新会员
echo -e "\n\n4. 更新会员"
curl -X PUT -H "Content-Type: application/json" -d '{
    "name": "测试会员1-更新",
    "company": "测试公司1-更新",
    "position": "测试职位1-更新",
    "phone": "13800138002",
    "email": "test1-update@example.com",
    "type": "COMPANY",
    "level": "DIRECTOR"
}' http://localhost:8080/api/members/1 -v | cat

# 5. 删除会员
echo -e "\n\n5. 删除会员"
curl -X DELETE http://localhost:8080/api/members/1 -v | cat 