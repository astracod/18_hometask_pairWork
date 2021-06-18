package com.newspring.delivery.dto.options.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.newspring.delivery.dto.options.simple.RoleDto;
import lombok.Data;

import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetAllRolesResponse {
    List<RoleDto> roles;
    private String status;
    private String error;
}
