package ru.isshepelev.jwt.entity.dto;

import lombok.Data;

@Data
public class UserRegistrationAndAuthenticateDto {
    private String username;
    private String password;
}
