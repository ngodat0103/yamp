FROM eclipse-temurin:17-alpine
WORKDIR /opt/app
COPY ./target/auth-service-0.0.1-SNAPSHOT.jar app.jar
RUN addgroup -S nonroot && adduser -S nonroot -G nonroot
RUN chown -R nonroot:nonroot /opt/app
USER nonroot
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "app.jar","--spring.profiles.active=docker-dev"]