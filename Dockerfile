FROM maven:3.9.8-eclipse-temurin-17-alpine AS builder
WORKDIR build
COPY . .
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre AS layers
WORKDIR /
COPY --from=builder /build/target/user-service-0.0.1-SNAPSHOT.jar app.jar
RUN java -Djarmode=tools -jar app.jar extract --layers --launcher --destination layers

FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
COPY --from=layers /layers/dependencies/ ./
COPY --from=layers /layers/spring-boot-loader/ ./
COPY --from=layers /layers/snapshot-dependencies/ ./
COPY --from=layers /layers/application/ ./
RUN useradd -r -s /usr/sbin/nologin -d /opt/app app && \
    chown -R app:app /opt/app
USER app
EXPOSE 7001
HEALTHCHECK --interval=30s --timeout=3s --retries=1 CMD wget -qO- http://localhost:7001/actuator/health/ | grep UP || exit 1
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]