package com.flab.makedel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/*
    @EnableRedisHttpSession : 기존 서버 세션 저장소를 사용하지 않고
    Redis의 Session Stroage에 Session을 저장하게 해줍니다.
    springSessionRepositoryFilter라는 이름의 필터를 스프링빈으로 생성합니다.
    springSessionRepositoryFilter 필터는 HttpSession을 스프링세션에 의해 지원되는 커스템 구현체로 바꿔줍니다.
    이 어노테이션이 붙은 곳에서는 레디스가 스프링 세션을 지원합니다.
 */

@SpringBootApplication
@EnableRedisHttpSession
public class DeliveryMakeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryMakeApplication.class, args);
    }
}
