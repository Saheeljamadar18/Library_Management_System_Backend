# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw && ./mvnw -q -DskipTests package

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/Library_Management_System-*.jar app.jar

EXPOSE 7777

ENTRYPOINT ["java", "-jar", "app.jar"]
