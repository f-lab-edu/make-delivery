package com.flab.makedel.controller;

import com.flab.makedel.aop.LoginCheck;
import com.flab.makedel.dto.UserDTO;
import com.flab.makedel.service.LoginService;
import com.flab.makedel.service.UserService;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
    @RestController @Controller에 @ResponseBody까지 합쳐진것입니다.
    주로 Http response로 view가 아닌 문자열과 JSON등을 보낼때 사용합니다.
    @RequestMapping 어노테이션은 URL을 컨트롤러의 클래스나 메서드와 매핑할 때 사용하는 스프링 프레임워크의 어노테이션입니다.
    @RequestBody는 HTTP 요청 body를 자바 객체로 변환합니다.

    request.getRequest은 해당 클라이언트의 세션이 없다면 생성해주고 있으면 반환해줍니다.
    메소드의 파라미터로 HttpSession을 받아온다면 위 과정을 스프링이 해줍니다.
*/

@RestController
@RequestMapping("/users")
public class UserController {

    private static final ResponseEntity<Void> RESPONSE_OK = new ResponseEntity(HttpStatus.OK);
    private static final ResponseEntity<Void> RESPONSE_CONFLICT = new ResponseEntity(
        HttpStatus.CONFLICT);
    private static final ResponseEntity<Void> RESPONSE_NOT_FOUND = new ResponseEntity(
        HttpStatus.NOT_FOUND);

    private final UserService userService;
    private final LoginService loginService;

    public UserController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @PostMapping
    public void signUp(UserDTO user) {
        userService.signUp(user);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Void> checkUniqueId(@PathVariable String id) {
        boolean isUniqueId = userService.isExistsId(id);
        if (isUniqueId) {
            return RESPONSE_OK;
        } else {
            return RESPONSE_CONFLICT;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(String id, String password) {

        Optional<UserDTO> user = userService.findUserByIdAndPassword(id, password);

        if (user.isPresent()) {
            loginService.loginUser(user.get().getId());
            return RESPONSE_OK;
        } else {
            return RESPONSE_NOT_FOUND;
        }

    }

    @GetMapping("/logout")
    @LoginCheck
    public ResponseEntity<Void> logout() {
        loginService.logoutUser();
        return RESPONSE_OK;
    }

}

/*
    컨트롤러 서비스 매퍼(dao)로 분리해서 사용하는 이유
    결국은 객체지향의 원칙을 따르려고 분리하는 것입니다. 비즈니스 로직을 분리하고 재사용을 가능하게 하기 위해서입니다.
    객체지향의 원칙인 단일책임 원칙을 지켜 서비스는 비즈니스 로직을 처리하게 하고
    컨트롤러는 http 요청에 따라 사용할 서비스를 선택하고 매퍼(dao)에서는 sql 쿼리를 분리시켜 각각의 책임을 가지게 하는 것입니다.

    또한 객체지향의 원칙인 개방폐쇄 원칙에 따라 서비스, 컨트롤러, 매퍼가 서로에 종속되지 않고
    스프링 빈을 통해 객체를 주입받아 객체들끼리 서로 종속되는 일이 없도록 하기 위해서입니다.
    스프링 프레임워크에서 스프링 빈은 객체의 라이프 사이클을 관리하기 때문에
    서비스 컨트롤러 매퍼 각각에서 객체를 생성하거나 다른객체에 의존하지 않고 스프링 프레임워크에 의해서 객체의 라이프사이클을 관리받아야 합니다.
    이러한 원칙들을 지키려면 컨트롤러 서비스 매퍼가 각각의 책임과 할일이 분명하게 나눠져야 합니다.

    컨트롤러에서 비즈니스 로직을 처리하기 위해 서비스를 불러오고 서비스에서 필요한 매퍼를 호출하여
    디비에 접근하는 식의 계층이 분리된 모델을 사용한다면 중복된 코드도 제거할 수 있습니다.

    컨트롤러에서 여러 서비스들을 재사용하기 용이하다.
    컨트롤러에서는 서비스 객체들을 주입받아 사용합니다. 비즈니스 로직이 데이터를 저장하고 수정하고 없애는 동작들을 여러 컨트롤러에서 사용할 가능성이 있으니까
    컨트롤러 내에서 처리하지 않고 서비스 객체들에게 위임해서 처리하고 컨트롤러는 처리된 결과를 받아 응답하는 것입니다.

    이러한 원칙들을 지킨다면 뷰에도 종속되지 않아 웹이던 앱이던 해당 비즈니스 로직을 그대로 가져갈 수 있습니다.
 */
