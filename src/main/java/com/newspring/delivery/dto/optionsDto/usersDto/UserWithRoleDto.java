package com.newspring.delivery.dto.optionsDto.usersDto;

import lombok.Data;

@Data
public class UserWithRoleDto {
    private Long id;
    private Long roleId;
    private String login;
    private String roleName;
}
