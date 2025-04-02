# Stage 1: Build Java application
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run Java application
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/Avante_garde_backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9900
ENTRYPOINT ["java", "-jar", "app.jar"]