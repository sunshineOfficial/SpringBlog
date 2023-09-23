package ru.pnzgu.springblog.dto.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
