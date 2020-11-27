FROM openjdk:8-jre-alpine
VOLUME /tmp
RUN apk --no-cache add curl
RUN apk --no-cache add jq


RUN curl -H "X-Vault-Token: s.Dsm16mhBp82Kw92FQLrxf4Rd" http://118.67.130.216:8200/v1/kv/sdk | jq -r .data > ccc.json

ENV SPRING_REDIS_PASSWORD=
ENV SPRING_DATASOURCE_PASSWORD=

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", \
"-Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD}", \
"-Dspring.redis.password=${SPRING_REDIS_PASSWORD}", \
"-Dspring.datasource.url=jdbc:mysql://172.17.0.1:3306/makedelivery?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", \
"-Dspring.redis.host=172.17.0.1", \
"-jar", \
"/app.jar"]