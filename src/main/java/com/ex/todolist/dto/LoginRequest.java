package com.ex.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // Todo: 제거하고싶음
@NoArgsConstructor
public class LoginRequest {

    private String username;
    private String password;

}