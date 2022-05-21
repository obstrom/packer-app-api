FROM openjdk:17-alpine

EXPOSE 8080

RUN mkdir /app

ARG JAR_FILE

COPY build/libs/$JAR_FILE /app/app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app/app.jar"]