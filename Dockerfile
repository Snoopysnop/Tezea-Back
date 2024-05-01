FROM maven:3.9.6-eclipse-temurin-21-alpine as build-step
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# overrides base image command
CMD 

FROM eclipse-temurin:21-alpine
WORKDIR /app/target
COPY --from=build-step /app/target .
EXPOSE 80
CMD ["java", "-jar", "tezea-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=qa"]