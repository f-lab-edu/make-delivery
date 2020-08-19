package com.flab.makedel.utils;

import javax.servlet.http.HttpSession;

/*
 *   세션은 UserController 뿐 아니라 많은 Controller에서 사용될 수 있기 때문에 SessionUtil
 *   클래스로 분리하였습니다. 컨트롤러에서 SessionUtil 클래스를 의존할 필요가 없고
 *   SessionUtil클래스를 인스턴스화 할 필요가 없기 때문에 메소드들을 static으로 선언하였습니다.
 * */

public class SessionUtil {

    private static final String USER_ID = "USER_ID";

    public static void setUserId(HttpSession session, String id) {
        session.setAttribute(USER_ID, id);
    }

    public static void deleteUserId(HttpSession session) {
        session.removeAttribute(USER_ID);
    }
}
