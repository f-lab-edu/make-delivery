package com.flab.makedel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *   Run-Time Weaving을 사용하는 다이나믹 프록시 방식의 SPRING AOP를 사용할 것이기 때문에
 *   Retention은 Runtime까지로 지정해줬습니다.
 *
 *   프록시 객체는 런타임에 생성하지만
 *   런타임에 그 프록시를 생성할 타겟 빈을 찾을때 클래스정보(바이트 코드)를 참고하기 때문에
 *   런타임까지 해당 어노테이션을 유지할 필요는 없습니다. Retention을 클래스까지로해도 똑같이 AOP적용이 가능합니다.
 * */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginCheck {

}
