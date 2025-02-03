# Build stage
FROM maven:3.9.6-amazoncorretto-21 AS builder
WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Build app
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Configuración de la aplicación
EXPOSE 8090
ENV API_VITALSIGNS_URL=http://signs:8083 \
    API_PATIENT_URL=http://patient:8082 \
    API_QUEUE_URL=http://queue:8086 \
    EXECUTION_TIME=300000

ENTRYPOINT ["java", "-jar", "app.jar"]