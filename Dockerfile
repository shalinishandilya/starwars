# Use an official OpenJDK runtime image as a base image for running the application
FROM openjdk:17-jdk-slim AS final

# Expose the port that the Spring Boot application will run on

EXPOSE 9092

ADD target/starwars.jar starwars.jar

# Define the command to run your Spring Boot application when the container starts

ENTRYPOINT ["java","-jar","/starwars.jar"]
