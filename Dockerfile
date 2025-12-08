# Use Java 17 (Render supports it)
FROM eclipse-temurin:17-jdk-alpine

# Set workdir
WORKDIR /app

# Copy Maven build files
COPY target/*.jar app.jar

# Expose your server port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
