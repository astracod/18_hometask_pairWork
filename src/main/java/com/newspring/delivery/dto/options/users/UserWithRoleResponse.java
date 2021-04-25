package com.newspring.delivery.dto.options.users;

import lombok.Data;

import java.util.List;
@Data
public class UserWithRoleResponse {
    private List<UserWithRoleDto> users;
    private String status;
    private String error;
}
