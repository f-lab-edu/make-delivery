package com.flab.makedel.service;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

/*
 *  SessionLoginService는 HttpSession에 직접 의존하지 않습니다.
 *  스프링이 SessionLoginService에 HttpSession 의존성 주입(DI)을 해줍니다.
 *  LoginService 인터페이스를 이용해 Session방식의 LoginService를 구현합니다.
 *  SessionLoginService는 singleton scope을 가지며 HttpSession은 session scope를 가집니다.
 *  이러한 스코프 차이 때문에 Spring이 HttpSession 인스턴스를 동적 프록시로 생성하여 주입합니다.
 *  이러한 기법은 Scoped Proxy라고 합니다.
 * */

@Service
public class SessionLoginService implements LoginService {

    private static final String USER_ID = "USER_ID";
    private final HttpSession session;

    public SessionLoginService(HttpSession session) {
        this.session = session;
    }

    public void loginUser(String id) {
        session.setAttribute(USER_ID, id);
    }

    public void logoutUser() {
        session.removeAttribute(USER_ID);
    }
}
