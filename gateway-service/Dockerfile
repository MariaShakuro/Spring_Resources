FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/gateway-service-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
LABEL maintainer="msh479859@gmail.com"
LABEL name="gateway-service"
