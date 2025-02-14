FROM openjdk:21

WORKDIR /usr/src/app

COPY target/security-0.0.1-SNAPSHOT.jar app.jar

COPY src/main/resources/application.properties application.properties

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
