FROM maven:3.8.8-eclipse-temurin-21 as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/passenger-service-0.0.1-SNAPSHOT.jar /app/passenger-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/passenger-service.jar"]
