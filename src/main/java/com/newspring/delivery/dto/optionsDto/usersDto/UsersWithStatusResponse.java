package com.newspring.delivery.dto.optionsDto.usersDto;

import com.newspring.delivery.dto.optionsDto.simple_dto.UserDto;

import lombok.Data;

import java.util.List;

@Data
public class UsersWithStatusResponse {
    private List<UserDto> users;
    private String status;
    private String error;
}
