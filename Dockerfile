
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY build/libs/dataHub-0.0.1.jar dataHub.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "dataHub.jar"]
