#!/bin/bash

echo "清理编译缓存..."
find . -name "*.class" -delete
rm -rf target/classes
mkdir -p target/classes

echo "编译Java文件..."
javac -cp "." -d target/classes \
    src/main/java/com/saip/enums/*.java \
    src/main/java/com/saip/entity/*.java \
    src/main/java/com/saip/common/*.java \
    src/main/java/com/saip/service/*.java \
    src/main/java/com/saip/service/impl/*.java \
    src/main/java/com/saip/controller/*.java \
    src/main/java/com/saip/*.java

if [ $? -eq 0 ]; then
    echo "编译成功！"
    echo "运行应用程序..."
    java -cp "target/classes" com.saip.SaipApplication
else
    echo "编译失败！"
    exit 1
fi 