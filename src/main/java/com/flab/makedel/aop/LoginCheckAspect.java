package com.flab.makedel.aop;

import com.flab.makedel.annotation.LoginCheck;
import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.service.LoginService;
import com.flab.makedel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

/*
 *   Aspect 클래스를 빈으로 등록하는 이유는 타깃을 DI받아야 하기 때문인 것 같다
 *   ProceedingJoinPoint같이 타깃 메소드들을 알려면 타깃을 DI받아야한다.
 *   InvocationHandler를 이용한 다이나믹 프록시 방식에서는 타깃을 직접 DI 받았지만
 *   아마 AspectJ에서 @Aspect를 선언하면 저절로 주입해주는 것 같다(?)
 *
 *   @Order 어노테이션을 적용하면 여러개의 AOP를 적용할 시 어떤 Advice를 먼저 사용할지 순서를
 *   정해줄 수 있습니다.
 * */

@Aspect
@Component
@RequiredArgsConstructor
@Order(-1)
public class LoginCheckAspect {

    private final LoginService loginService;
    private final UserService userService;

    @Before("@annotation(com.flab.makedel.annotation.LoginCheck) && @annotation(target)")
    public void loginCheck(LoginCheck target) throws HttpClientErrorException {
        
        if (target.userLevel() == UserLevel.USER) {
            userLoginCheck();
        } else if (target.userLevel() == UserLevel.OWNER) {
            ownerLoginCheck();
        }

    }

    public String getCurrentUser() throws HttpClientErrorException {

        String userId = loginService.getCurrentUser();
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }

        return userId;

    }

    public void userLoginCheck() {

        String userId = getCurrentUser();

        UserLevel level = userService.findUserById(userId).getLevel();

        if (!(level == UserLevel.USER)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }

    }

    public void ownerLoginCheck() {

        String userId = getCurrentUser();

        UserLevel level = userService.findUserById(userId).getLevel();

        if (!(level == UserLevel.OWNER)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }

    }

}

