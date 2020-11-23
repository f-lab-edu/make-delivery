FROM openjdk:8-jre-alpine
VOLUME /tmp
ARG JAR_FILE=/var/lib/jenkins/make-delivery/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]