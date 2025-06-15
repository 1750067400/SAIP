FROM openjdk:11-jdk-slim

WORKDIR /app

COPY /backend/target/saip-backend-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
