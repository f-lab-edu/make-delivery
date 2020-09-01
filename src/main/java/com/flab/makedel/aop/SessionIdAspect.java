package com.flab.makedel.aop;

import com.flab.makedel.dto.CurrentUserDTO;
import com.flab.makedel.service.LoginService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class SessionIdAspect {

    private final LoginService loginService;

    @Around("execution(* *(.., @SessionId (*), ..))")
    public Object getSessionId(ProceedingJoinPoint pjp) throws Throwable {

        String sessionId = loginService.getCurrentUser();

        CurrentUserDTO currentUser = CurrentUserDTO.builder().id(sessionId).build();
        Object[] args = Arrays.stream(pjp.getArgs()).map(arg -> {
            if (arg instanceof CurrentUserDTO) {
                arg = currentUser;
            }
            return arg;
        }).toArray();

        return pjp.proceed(args);
    }
}
