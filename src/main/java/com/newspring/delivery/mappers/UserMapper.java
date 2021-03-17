package com.newspring.delivery.mappers;

import com.newspring.delivery.dto.options_with_user.*;
import com.newspring.delivery.dto.options_with_user.simple_dto.RoleDto;
import com.newspring.delivery.dto.options_with_user.simple_dto.UserDto;
import com.newspring.delivery.entities.ChangeRoleOnUser;
import com.newspring.delivery.entities.Role;
import com.newspring.delivery.entities.User;
import com.newspring.delivery.entities.UserWithRole;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {


    public User toAddUser(AddUserRequest req) {
        return new User(null, req.getLogin(), req.getPassword(), req.getRoleId(), req.getPhone());
    }
    public ChangeRoleOnUser toUpdateRole(UserRoleUpdateResponse res ){
        return new ChangeRoleOnUser(res.getId(),res.getRoleId());
    }

    public RoleDto toRoleDto(Role role) {
        RoleDto response = new RoleDto();
        response.setId(role.getId());
        response.setName(role.getName());
        return response;
    }

    public UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setRoleId(user.getRoleId());
        dto.setPhone(user.getPhone());
        return dto;
    }

    public GetAllRolesResponse toAllRolesResponse(List<Role> roles) {
        GetAllRolesResponse response = new GetAllRolesResponse();
        response.setStatus("OK");
        response.setRoles(roles.stream()
                .map(this::toRoleDto)
                .collect(Collectors.toList()));
        return response;
    }

    public UsersWithStatusResponse toStatusResponse(List<User> users) {
        UsersWithStatusResponse response = new UsersWithStatusResponse();
        response.setStatus("OK");
        response.setUsers(users.stream()
                .map(this::toUserDto)
                .collect(Collectors.toList()));
        return response;
    }

    public UserWithRoleDto toUserWithRoleDto(UserWithRole user) {
        UserWithRoleDto userWithRoleDto = new UserWithRoleDto();
        userWithRoleDto.setId(user.getId());
        userWithRoleDto.setLogin(user.getLogin());
        userWithRoleDto.setRoleId(user.getRoleId());
        userWithRoleDto.setRoleName(user.getRoleName());
        return userWithRoleDto;
    }

    public UserWithRoleResponse toUserWithRoleResponse(List<UserWithRole> users) {
        UserWithRoleResponse users1 = new UserWithRoleResponse();
        users1.setStatus("OK");
        users1.setUsers(users.stream().map(u -> toUserWithRoleDto(u)).collect(Collectors.toList()));
        return users1;
    }
}

