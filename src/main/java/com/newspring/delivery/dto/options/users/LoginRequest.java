package com.newspring.delivery.dto.options.users;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String password;
}
