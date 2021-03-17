package com.newspring.delivery.dto.options_with_user;

import lombok.Data;

@Data
public class AddUserRequest {
    private String login;
    private String password;
    private Long roleId;
    private String phone;
}
