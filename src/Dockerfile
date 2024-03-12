# Use the official OpenJDK image as a parent image
FROM openjdk:11-jdk

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the Gradle configuration files (including gradlew and gradle wrapper)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy the source code of your application
COPY src src

# Give execute permissions to the Gradle wrapper
RUN chmod +x ./gradlew

# Use the Gradle wrapper to build the application
RUN ./gradlew build -x test

# Expose the port your app runs on
EXPOSE 9090

# Command to run your app
CMD ["java", "-jar", "build/libs/your-application-0.0.1-SNAPSHOT.jar"]