package com.newspring.delivery.dto.options_with_user;

import lombok.Data;

@Data
public class UserWithRoleDto {
    private Long id;
    private Long roleId;
    private String login;
    private String roleName;
}
