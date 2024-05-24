# Use an official OpenJDK runtime as a parent image
FROM maven:3.9.6-eclipse-temurin-21-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files to the container
COPY pom.xml .

# Download the dependencies
RUN mvn dependency:go-offline -B

# Copy the project source code to the container
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Expose the port your application runs on
EXPOSE 8080

# Set the active Spring profile
ENV SPRING_PROFILES_ACTIVE dev

# Run the application
ENTRYPOINT ["java", "-jar", "target/rate-your-books-0.0.1-SNAPSHOT.jar"]
