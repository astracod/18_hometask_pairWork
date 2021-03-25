package com.newspring.delivery.dto.optionsDto.usersDto;

import com.newspring.delivery.dto.optionsDto.simple_dto.RoleDto;
import lombok.Data;

import java.util.List;
@Data
public class GetAllRolesResponse {
    List<RoleDto> roles;
    private String status;
    private String error;
}
