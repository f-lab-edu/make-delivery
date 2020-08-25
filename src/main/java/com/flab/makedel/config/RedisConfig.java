package com.flab.makedel.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/*
    @Value : Spring이 지원하는 의존성 주입 방법중 하나입니다.
    SpEL을 지원하며 application.properties의 속성값을 프로퍼티에 넣어줍니다.

    RedisConnectionFactory을 생성하여 스프링 세션을 레디스 서버로 연결시킵니다.
    RedisConnectionFactory는 Connection 객체를 생성하여 관리하는 인터페이스입니다.
    RedisConnection을 리턴합니다. RedisConnection은 Redis 서버와의 통신을 추상화합니다.

    Redis Connection Factory를 리턴해주는 라이브러리로 Lettuce를 선택하였습니다.
    비동기로 요청하기 때문에 높은성능을 가지기 때문입니다.
 */

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setPassword(redisPassword);
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(
            redisStandaloneConfiguration);
        return lettuceConnectionFactory;
    }

}


/*
    레디스의 키값으로 저장되는 타입입니다.
    spring:session:sessions:expires (string) 해당 세션의 만료키로 사용합니다.
    spring:session:expirations (set) expire time에 삭제될 세션 정보를 담고 있습니다.
    spring:session:sessions (hash) session은 map을 저장소로 사용하기 때문에 이곳에 세션 데이터가 있습니다.
 */