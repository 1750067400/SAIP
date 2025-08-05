@echo off
echo 启动产业园促进会管理系统后端...

REM 检查Java版本
java -version >nul 2>&1
if errorlevel 1 (
    echo 错误: 未找到Java，请先安装Java 17或更高版本
    pause
    exit /b 1
)

REM 检查Maven是否安装
mvn -version >nul 2>&1
if errorlevel 1 (
    echo 错误: 未找到Maven，请先安装Maven
    pause
    exit /b 1
)

REM 清理并编译项目
echo 编译项目...
mvn clean compile
if errorlevel 1 (
    echo 编译失败，请检查错误信息
    pause
    exit /b 1
)

REM 启动应用
echo 启动应用...
mvn spring-boot:run

echo 应用已启动，访问地址: http://localhost:8080/api
echo 测试API: http://localhost:8080/api/test/ping
pause 