FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY target/driver-service-0.0.1.jar app.jar

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
LABEL maintainer="msh479859@gmail.com"
LABEL name="driver-service"
