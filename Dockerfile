FROM openjdk:8-jre-alpine
VOLUME /tmp
ARG JAR_FILE=make-delivery/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]