package com.newspring.delivery.dto.optionsDto.usersDto;

import lombok.Data;

@Data
public class LoginForTokenDto {
    private Long id;
    private String login;
    private String pass;
    private String roleName;
}
