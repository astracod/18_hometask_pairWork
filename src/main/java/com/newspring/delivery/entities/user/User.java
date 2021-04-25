package com.newspring.delivery.entities.user;

import lombok.Data;

@Data
public class User {
  private Long id;
  private String login;
  private String password;
  private Long roleId;
  private String phone;
  private String ip;
  private String roleName;
  private String message;
}
