# Stage 1: Build
FROM openjdk:21-slim as build

WORKDIR /app

# Copy the Gradle wrapper files
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Ensure the Gradle wrapper is executable
RUN chmod +x ./gradlew

# Install necessary dependencies for building
RUN apt-get update && apt-get install -y dos2unix

# Convert gradlew to Unix format (in case it is in Windows format)
RUN dos2unix ./gradlew

# Verify if gradlew is executable
RUN ls -l ./gradlew

# run dependencies task
RUN ./gradlew dependencies

# Copy the source code and build the application
COPY src src

# Build the application JAR
RUN ./gradlew bootJar --info

# Stage 2: Run
FROM openjdk:21-slim

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Copy .env file
COPY .env /app/.env

# Install envsubst for environment variable substitution
RUN apt-get update && apt-get install -y gettext-base && apt-get clean

# Expose application port
EXPOSE 8080

# Entry point script
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

# Set entrypoint
ENTRYPOINT ["/app/entrypoint.sh"]
