package com.test.apiTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    private Long id;
    private String name;
    private String username;
    private String role;
    private String email;
    private String entity;
}
