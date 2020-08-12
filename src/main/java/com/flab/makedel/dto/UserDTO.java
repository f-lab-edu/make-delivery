package com.flab.makedel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class UserDTO {

    private String id;

    private String password;

    private String email;

    private String name;

    private String phone;

    private String address;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}

