package com.flab.makedel.service;

/*
 *  로그인 하는 로직은 여러 Controller에서 쓰일 수 있기 때문에 LoginService로 구현하였습니다.
 *  지금은 세션 방식으로 구현하였으나 나중에 토큰이나 여러 형태로 구현이 가능하기 떄문에
 *  LoginService 인터페이스로 Loose Coupling을 통해 컨트롤러가 로그인 서비스를 간접적으로 의존하게 하였습니다.
 *  컨트롤러는 어떤방식(세션,토큰등)으로 로그인을 구현하였는지 알 필요가 없으며 interface 메소드만 사용하면 됩니다.
 * */

public interface LoginService {

    void setUserId(String id);

    void deleteUserId();

}
