package com.newspring.delivery.dto.options_with_user;

import com.newspring.delivery.dto.options_with_user.simple_dto.RoleDto;
import lombok.Data;

import java.util.List;
@Data
public class GetAllRolesResponse {
    List<RoleDto> roles;
    private String status;
    private String error;
}
