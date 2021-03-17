package com.newspring.delivery.dto.options_with_user;

import com.newspring.delivery.dto.options_with_user.simple_dto.UserDto;
import lombok.Data;

import java.util.List;

@Data
public class UsersWithStatusResponse {
    private List<UserDto> users;
    private String status;
    private String error;
}
