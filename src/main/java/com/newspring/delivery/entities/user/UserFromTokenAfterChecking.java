package com.newspring.delivery.entities.user;

import lombok.Data;

@Data
public class UserFromTokenAfterChecking {
    private Long id;
    private String login;
    private String pass;
    private String roleName;
    private String ip;
    private String message;
}
