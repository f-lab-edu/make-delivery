package com.flab.makedel.controller;

import com.flab.makedel.dto.UserDTO;
import com.flab.makedel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller에 @ResponseBody까지 합쳐진것입니다. 주로 Http response로 view가 아닌 문자열과 JSON등을 보낼때 사용합니다.
@RequestMapping("/users")//  @RequestMapping 어노테이션은 URL을 컨트롤러의 클래스나 메서드와 매핑할 때 사용하는 스프링 프레임워크의 어노테이션입니다.
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping //@RequestParam은 파라미터를 1:1로 받아온다. @ModelAttribute는 단순 타입 데이터들을 복합 타입 객체로 받아올 수 있다. @ModelAttribute 생략해도 저절로 받아올수있다.
    public ResponseEntity<Void> signUp(@ModelAttribute UserDTO user) {
        userService.signUp(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> checkUniqueId(@PathVariable String id)
    {
        boolean isUniqueId=userService.checkUniqueId(id);
        String checkUniqueIdMessage;
        if(isUniqueId) checkUniqueIdMessage="사용가능한 아이디입니다.";
        else checkUniqueIdMessage="중복된 아이디이므로 사용불가합니다.";
        return new ResponseEntity(checkUniqueIdMessage,HttpStatus.OK);
    }

}

