# Step 1: Use a lightweight base image that includes Java
FROM eclipse-temurin:17

#Add Metadata
LABEL maintainer="hi@dami.bio"

# Step 2: Set a working directory inside the container
WORKDIR /app

# Step 3: Copy the built JAR file into the container
COPY target/student-management-system-0.0.1-SNAPSHOT.jar /app/app.jar

# Step 4: Define the startup command
ENTRYPOINT ["java", "-jar", "app.jar"]
