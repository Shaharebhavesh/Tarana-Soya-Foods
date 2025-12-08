# ===========================
# Stage 1: Build the JAR file
# ===========================
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Copy project files
COPY . .

# Build Spring Boot app
RUN ./mvnw clean package -DskipTests

# ===========================
# Stage 2: Run the app
# ===========================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
