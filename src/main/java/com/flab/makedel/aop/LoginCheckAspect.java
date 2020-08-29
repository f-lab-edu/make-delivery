package com.flab.makedel.aop;

import com.flab.makedel.service.LoginService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

/*
 *   Aspect 클래스를 빈으로 등록하는 이유는 타깃을 DI받아야 하기 때문인 것 같다
 *   ProceedingJoinPoint같이 타깃 메소드들을 알려면 타깃을 DI받아야한다.
 *   InvocationHandler를 이용한 다이나믹 프록시 방식에서는 타깃을 직접 DI 받았지만
 *   아마 AspectJ에서 @Aspect를 선언하면 저절로 주입해주는 것 같다(?)
 * */

@Aspect
@Component
public class LoginCheckAspect {

    private final LoginService loginService;

    public LoginCheckAspect(LoginService loginService) {
        this.loginService = loginService;
    }

    @Before("@annotation(LoginCheck)")
    public void loginCheck() throws HttpClientErrorException {
        String userId = loginService.getUser();
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

}

