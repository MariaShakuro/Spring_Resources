FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/passenger-service-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
LABEL maintainer="msh479859@gmail.com"
LABEL name="passenger-service"