package com.newspring.delivery.dto.options.users;

import lombok.Data;

@Data
public class AddUserRequest {
    private String login;
    private String password;
    private Long roleId;
    private String phone;
}
