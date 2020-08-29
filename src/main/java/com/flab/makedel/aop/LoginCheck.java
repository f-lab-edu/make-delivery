package com.flab.makedel.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *   Run-Time Weaving을 사용하는 다이나믹 프록시 방식의 SPRING AOP를 사용할 것이기 때문에
 *   Retention은 Runtime까지로 지정해줬습니다.
 * */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginCheck {

}
