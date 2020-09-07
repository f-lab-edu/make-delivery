package com.flab.makedel.aop;

import com.flab.makedel.service.StoreService;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

/*
    이 Store 현재 로그인한 Owner가 소유한 Store가 맞는지 체크하는 Aspect입니다.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class CheckMyStoreAspect {

    private final StoreService storeService;

    @Before("@annotation(com.flab.makedel.annotation.CheckMyStore)")
    public void checkMyStore(JoinPoint jp)
        throws HttpClientErrorException {

        Object[] args = jp.getArgs();
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();

        Long storeId = null;
        String ownerId = "";

        for (int i = 0; i < method.getParameters().length; i++) {
            if (method.getParameters()[i].getName().equals("storeId")) {
                storeId = (Long) args[i];
            } else if (method.getParameters()[i].getName().equals("ownerId")) {
                ownerId = (String) args[i];
            }
        }

        boolean isMyStore = storeService.checkMyStore(storeId, ownerId);
        if (!isMyStore) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }


}
