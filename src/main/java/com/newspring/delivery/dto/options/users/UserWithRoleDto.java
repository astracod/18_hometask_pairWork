package com.newspring.delivery.dto.options.users;

import lombok.Data;

@Data
public class UserWithRoleDto {
    private Long id;
    private Long roleId;
    private String login;
    private String password;
    private String phone;
    private String roleName;
}
