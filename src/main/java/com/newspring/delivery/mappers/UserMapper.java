package com.newspring.delivery.mappers;

import com.newspring.delivery.dto.optionsDto.simple_dto.RoleDto;
import com.newspring.delivery.dto.optionsDto.simple_dto.UserDto;
import com.newspring.delivery.dto.optionsDto.usersDto.*;

import com.newspring.delivery.entities.user.ChangeRoleOnUser;
import com.newspring.delivery.entities.user.Role;
import com.newspring.delivery.entities.user.User;
import com.newspring.delivery.entities.user.UserWithRole;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {


    public User toUser(AddUserRequest req) {
        return new User(null, req.getLogin(), req.getPassword(), req.getRoleId(), req.getPhone());
    }

    public ChangeRoleOnUser toUpdateRole(UserRoleUpdateResponse res) {
        return new ChangeRoleOnUser(res.getId(), res.getRoleId());
    }


    public UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setRoleId(user.getRoleId());
        dto.setPhone(user.getPhone());
        return dto;
    }

    public UsersWithStatusResponse toStatusResponse(List<User> users) {
        UsersWithStatusResponse response = new UsersWithStatusResponse();
        response.setStatus("OK");
        response.setUsers(users.stream()
                .map(this::toUserDto)
                .collect(Collectors.toList()));
        return response;
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

