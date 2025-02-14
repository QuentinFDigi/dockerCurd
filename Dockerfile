FROM openjdk:21

WORKDIR /usr/src/app

COPY target/security-0.0.1-SNAPSHOT.jar .

RUN java -jar ./security-0.0.1-SNAPSHOT.jar

EXPOSE 8080
