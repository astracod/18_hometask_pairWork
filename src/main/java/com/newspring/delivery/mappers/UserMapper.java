package com.newspring.delivery.mappers;

import com.newspring.delivery.dto.options.simple.RoleDto;
import com.newspring.delivery.dto.options.users.*;
import com.newspring.delivery.entities.user.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {


    public User toUser(AddUserRequest req) {
        User user = new User();
        user.setLogin(req.getLogin());
        user.setPassword(req.getPassword());
        user.setRoleId(req.getRoleId());
        user.setPhone(req.getPhone());
        return user;
    }

    public ChangeRoleOnUser toUpdateRole(UserRoleUpdateResponse res) {
        return new ChangeRoleOnUser(res.getId(), res.getRoleId());
    }

// изменил класс на Roles
    public RoleDto toRoleDto(Roles role) {
        RoleDto response = new RoleDto();
        response.setId(role.getId());
        response.setName(role.getName());
        return response;
    }
    // изменил класс на Roles
    public GetAllRolesResponse toAllRolesResponse(List<Roles> roles) {
        GetAllRolesResponse response = new GetAllRolesResponse();
        response.setStatus("OK");
        response.setRoles(roles.stream()
                .map(r -> toRoleDto(r))
                .collect(Collectors.toList()));
        return response;
    }


    public UserWithRoleDto toUserWithRoleDto(User user) {
        UserWithRoleDto userWithRoleDto = new UserWithRoleDto();
        userWithRoleDto.setId(user.getId());
        userWithRoleDto.setLogin(user.getLogin());
        userWithRoleDto.setPassword(user.getPassword());
        userWithRoleDto.setPhone(user.getPhone());
        userWithRoleDto.setRoleId(user.getRoleId());
        userWithRoleDto.setRoleName(user.getRoles().getName());
        return userWithRoleDto;
    }

    public UserWithRoleResponse toUserWithRoleResponse(List<User> users) {
        UserWithRoleResponse users1 = new UserWithRoleResponse();
        users1.setStatus("OK");
        users1.setUsers(users.stream().map(u -> toUserWithRoleDto(u)).collect(Collectors.toList()));
        return users1;
    }

}

