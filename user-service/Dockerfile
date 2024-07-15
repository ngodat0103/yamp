FROM openjdk:17
LABEL authors="DatNVM"
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]
