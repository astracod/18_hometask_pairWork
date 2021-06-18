package com.newspring.delivery.dto.options.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddUserRequest {
    private String login;
    private String password;
    private String phone;
}
