# ===== Stage 1: Build the JAR =====
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies (caching step)
COPY /backend/pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code and build
COPY /backend/src ./src
RUN mvn clean package -DskipTests

# ===== Stage 2: Run the JAR =====
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
