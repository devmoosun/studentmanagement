# Step 1: Use Maven to build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven descriptor first (for caching)
COPY pom.xml .

# Download dependencies (cached between builds)
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the Spring Boot application (skip tests for speed)
RUN mvn clean package -DskipTests

# Step 2: Use a lightweight Java image to run the app
FROM eclipse-temurin:17-jdk-alpine

# Set a working directory
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Add metadata (optional)
LABEL maintainer="hi@dami.bio"

# Define the startup command
ENTRYPOINT ["java", "-jar", "app.jar"]
