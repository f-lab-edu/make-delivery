FROM openjdk:8-jre-alpine
VOLUME /tmp
RUN apk --no-cache add curl \
 && apk --no-cache add jq

RUN curl -H "X-Vault-Token: s.Dsm16mhBp82Kw92FQLrxf4Rd" http://118.67.130.216:8200/v1/kv/sdk | jq -r .data > firebaseSDK.json

ENV SPRING_REDIS_PASSWORD=
ENV SPRING_REDIS_HOST=
ENV SPRING_DATASOURCE_PASSWORD=
ENV SPRING_DATASOURCE_URL=

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", \
"-Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD}", \
"-Dspring.redis.password=${SPRING_REDIS_PASSWORD}", \
"-Dspring.datasource.url=${SPRING_DATASOURCE_URL}", \
"-Dspring.redis.host=${SPRING_REDIS_HOST}", \
"-jar", \
"/app.jar"]