FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /project
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /project
COPY --from=builder /project/target/project-0.0.1-SNAPSHOT.jar project.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "project.jar"]

