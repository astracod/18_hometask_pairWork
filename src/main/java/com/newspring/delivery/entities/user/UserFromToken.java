package com.newspring.delivery.entities.user;

import lombok.Data;

@Data
public class UserFromToken {
    private String login;
    private String pass;
}
