#!/bin/bash

echo "创建Maven标准目录结构..."
mkdir -p src/main/resources
mkdir -p src/test/java
mkdir -p src/test/resources

echo "移动Java文件到标准目录..."
mv src/main/java/com/saip/* src/main/java/com/saip/ 2>/dev/null || true

echo "创建application.properties..."
cat > src/main/resources/application.properties << EOF
# 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/saip?useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA配置
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

# 服务器配置
server.port=8080
server.servlet.context-path=/api

# 日志配置
logging.level.root=INFO
logging.level.com.saip=DEBUG
EOF

echo "更新.gitignore..."
cat > .gitignore << EOF
HELP.md
target/
!.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

### STS ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache

### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/
build/
!**/src/main/**/build/
!**/src/test/**/build/

### VS Code ###
.vscode/
EOF

echo "迁移完成！" 