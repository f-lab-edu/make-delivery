package com.flab.makedel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/*
    @EnableRedisHttpSession : 기존 서버 세션 저장소를 사용하지 않고
    Redis의 Session Stroage에 Session을 저장하게 해줍니다.
    springSessionRepositoryFilter라는 이름의 필터를 스프링빈으로 생성합니다.
    springSessionRepositoryFilter 필터는 HttpSession을 스프링세션에 의해 지원되는 커스템 구현체로 바꿔줍니다.
    이 어노테이션이 붙은 곳에서는 레디스가 스프링 세션을 지원합니다.

    @EnableAspectJAutoProxy:
    AOP를 RTW인 다이나믹 프록시 방식으로 사용가능하게 해줍니다.
    @SpringbootAplication 어노테이션 안에 @EnableAutoConfiguration이 있고
    이 어노테이션은 스프링부트가 클래스패스에서 찾은 빈들을 설정하게 해줍니다.
    따라서 스프링이 @Aspect를 보고 프록시방식으로 사용하게 해주기 때문에
    @EnableAspectJAutoProxy가 생략가능합니다.

    @EnableCaching: 스프링에 AOP로 구현되어있는 캐시 로직을 사용하게 해줍니다.
    스프링에서 제공해주는 AOP가 없다면 캐시를 사용할 때 직접 부가기능을 AOP로 구현해야합니다.
    스프링이 CacheManager 인터페이스를 추상화하였기 때문에 RedisCacheManager,EhCacheManager등
    필요한 CacheManage로 갈아끼워 사용할 수 있습니다.
 */

@SpringBootApplication
@EnableRedisHttpSession
@EnableCaching
public class DeliveryMakeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryMakeApplication.class, args);
    }
}
