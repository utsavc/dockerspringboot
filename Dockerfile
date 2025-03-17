# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/dockerspringboot-0.0.1-SNAPSHOT.jar dockerspringboot.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run your application
ENTRYPOINT ["java", "-jar", "dockerspringboot.jar"]