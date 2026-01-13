FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /lab5
COPY Docker-1/ .
RUN mvn package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /lab5
COPY --from=build /lab5/target/demo-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
