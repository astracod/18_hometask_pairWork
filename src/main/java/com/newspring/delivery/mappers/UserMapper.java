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
        user.setPhone(req.getPhone());
        return user;
    }

    public ChangeRoleOnUser toUpdateRole(UserRoleUpdateResponse res) {
        return new ChangeRoleOnUser(res.getId(), res.getRoleId());
    }


    public RoleDto toRoleDto(Role role) {
        RoleDto response = new RoleDto();
        response.setId(role.getId());
        response.setName(role.getName());
        return response;
    }

    public GetAllRolesResponse toAllRolesResponse(List<Role> roles) {
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
        userWithRoleDto.setRoleId(user.getRole().getId());
        userWithRoleDto.setRoleName(user.getRole().getName());
        return userWithRoleDto;
    }

    public UserWithRoleResponse toUserWithRoleResponse(List<User> users) {
        UserWithRoleResponse users1 = new UserWithRoleResponse();
        users1.setStatus("OK");
        users1.setUsers(users.stream().map(u -> toUserWithRoleDto(u)).collect(Collectors.toList()));
        return users1;
    }

}

