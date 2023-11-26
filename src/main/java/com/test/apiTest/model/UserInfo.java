package com.test.apiTest.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;

    private String email;

    private String password;

    private String role;

    private String entity;
}
