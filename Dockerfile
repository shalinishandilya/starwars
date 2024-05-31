# Use an official Maven image as a base image for building the project
FROM maven:3.8.3-openjdk-17 AS builder


# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project definition file (pom.xml) to the working directory
COPY pom.xml .

# Copy the entire source code to the working directory
COPY src ./src

# Run Maven clean install to build the project
RUN mvn clean install

# Use an official OpenJDK runtime image as a base image for running the application
FROM openjdk:17-jdk-slim AS final

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged Spring Boot application JAR file into the container from the builder stage
COPY --from=builder /app/target/starwars.jar ./starwars.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Define the command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "starwars.jar"]
