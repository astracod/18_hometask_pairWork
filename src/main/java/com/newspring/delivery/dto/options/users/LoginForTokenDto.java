package com.newspring.delivery.dto.options.users;

import lombok.Data;

@Data
public class LoginForTokenDto {
    private Long id;
    private String login;
    private String pass;
    private String roleName;
}
