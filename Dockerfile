FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y curl

WORKDIR /app

COPY target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]