package com.flab.makedel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/*
@Builder는 DTO 생성 시, 빌더 클래스를 자동으로 추가해준다. 빌더 클래스는 멤버변수별 메소드가 있고
이는 변수에 값을 set하고 이값을 통하여 build() 메소드를 통해 멤버변수에 필수값들을 null체크하고
이 멤버변수값을 이용해 빌더 클래스의 생성자를 호출하고 인스턴스를 리턴한다.
*/

@Getter
@AllArgsConstructor
@Builder
public class UserDTO {

    private final String id;

    private final String password;

    private final String email;

    private final String name;

    private final String phone;

    private final String address;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;
}
