package com.newspring.delivery.dto.optionsDto.simple_dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String login;
    private Long roleId;
    private String phone;
}
