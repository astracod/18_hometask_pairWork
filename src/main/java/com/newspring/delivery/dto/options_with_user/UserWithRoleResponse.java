package com.newspring.delivery.dto.options_with_user;

import lombok.Data;

import java.util.List;
@Data
public class UserWithRoleResponse {
    private List<UserWithRoleDto> users;
    private String status;
    private String error;
}
