
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY /home/uk/build/libs/dataHub-0.0.1.jar dataHub.jar

ENTRYPOINT ["java", "-jar", "dataHub.jar"]
