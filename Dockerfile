FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN apk --no-cache add curl \
 && apk --no-cache add jq

ENV SPRING_REDIS_PASSWORD=${SPRING_REDIS_PASSWORD}
ENV SPRING_REDIS_HOST=${SPRING_REDIS_HOST}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV VAULT_TOKEN=${VAULT_TOKEN}
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64


RUN curl -H "X-Vault-Token: s.Dsm16mhBp82Kw92FQLrxf4Rd" \
http://118.67.130.216:8200/v1/kv/sdk | jq .data > firebaseSDK.json

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", \
"-Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD}", \
"-Dspring.redis.password=${SPRING_REDIS_PASSWORD}", \
"-Dspring.datasource.url=${SPRING_DATASOURCE_URL}", \
"-Dspring.redis.host=${SPRING_REDIS_HOST}", \
"-Dcom.sun.management.jmxremote", \
"-Dcom.sun.management.jmxremote.port=9090", \
"-Dcom.sun.management.jmxremote.authenticate=false", \
"-Dcom.sun.management.jmxremote.ssl=false", \
"-Dcom.sun.management.jmxremote.rmi.port=9090", \
"-Djava.rmi.server.hostname=101.101.218.78", \
"-jar", \
"/app.jar"]