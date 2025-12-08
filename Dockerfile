# ===========================
# Stage 1: Build the JAR file
# ===========================
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Install Maven (because you don't have mvnw in your repo)
RUN apk add --no-cache maven

# Copy everything to the container
COPY . .

# Build Spring Boot app
RUN mvn clean package -DskipTests

# ===========================
# Stage 2: Run the app
# ===========================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy only the generated JAR
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
