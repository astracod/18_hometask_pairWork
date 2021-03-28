package com.newspring.delivery.dto.optionsDto.usersDto;

import lombok.Data;

import java.util.List;
@Data
public class UserWithRoleResponse {
    private List<UserWithRoleDto> users;
    private String status;
    private String error;
}
