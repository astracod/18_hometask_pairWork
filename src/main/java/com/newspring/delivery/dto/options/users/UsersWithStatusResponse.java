package com.newspring.delivery.dto.options.users;

import com.newspring.delivery.dto.options.simple.UserDto;

import lombok.Data;

import java.util.List;

@Data
public class UsersWithStatusResponse {
    private List<UserDto> users;
    private String status;
    private String error;
}
