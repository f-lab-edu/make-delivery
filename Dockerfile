FROM openjdk:8-jre-alpine
VOLUME /tmp
RUN apk --no-cache add curl \
 && apk --no-cache add jq

ENV SPRING_REDIS_PASSWORD=${SPRING_REDIS_PASSWORD}
ENV SPRING_REDIS_HOST=${SPRING_REDIS_HOST}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV VAULT_TOKEN=${VAULT_TOKEN}

RUN VAULT_TOKEN=${VAULT_TOKEN}
RUN curl -H ${VAULT_TOKEN} \
http://118.67.130.216:8200/v1/kv/sdk | jq -r .data > firebaseSDK.json

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", \
"-Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD}", \
"-Dspring.redis.password=${SPRING_REDIS_PASSWORD}", \
"-Dspring.datasource.url=${SPRING_DATASOURCE_URL}", \
"-Dspring.redis.host=${SPRING_REDIS_HOST}", \
"-jar", \
"/app.jar"]