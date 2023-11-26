package com.test.apiTest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginResponse {
    private String name;

    private String email;

    private String role;

    private String entity;
}
