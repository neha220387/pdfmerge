# Multi-stage build
FROM maven:3.9.0-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Final stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/pdf-merge-app-1.0.0.jar ./pdf-merge-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "pdf-merge-app.jar"]
