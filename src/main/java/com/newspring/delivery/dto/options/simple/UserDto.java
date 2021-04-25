package com.newspring.delivery.dto.options.simple;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String login;
    private Long roleId;
    private String phone;
}
