package com.newspring.delivery.dto.options.users;

import com.newspring.delivery.dto.options.simple.RoleDto;
import lombok.Data;

import java.util.List;
@Data
public class GetAllRolesResponse {
    List<RoleDto> roles;
    private String status;
    private String error;
}
