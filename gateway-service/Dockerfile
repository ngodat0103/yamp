FROM maven:3.9.8-eclipse-temurin-17-alpine
WORKDIR /opt/app
COPY ./target/gateway-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]