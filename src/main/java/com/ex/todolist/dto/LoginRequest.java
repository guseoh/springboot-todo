package com.ex.todolist.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  로그인 DTO
 */
@Getter
@Setter // Todo: 제거하고싶음
@NoArgsConstructor
public class LoginRequest {

    private String username;
    private String password;

}