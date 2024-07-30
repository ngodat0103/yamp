FROM maven:3.9.8-eclipse-temurin-17-alpine
WORKDIR /opt/app
COPY ./target/auth-service-0.0.1-SNAPSHOT.jar app.jar
RUN addgroup -S auth-service && adduser -S auth-service -G auth-service
RUN chown -R auth-service:auth-service /opt/app && chmod -R 755 /opt/app
USER auth-service
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "app.jar"]