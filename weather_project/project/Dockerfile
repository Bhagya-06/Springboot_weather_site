FROM maven:3.9.6-eclipse-temurin-24 AS builder
WORKDIR /app
COPY . .
WORKDIR /app/weather_project/project
RUN mvn clean package -DskipTests

FROM eclipse-temurin:24-jdk-alpine
WORKDIR /app
COPY --from=builder /app/weather_project/project/target/project-0.0.1-SNAPSHOT.jar project.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "project.jar"]
