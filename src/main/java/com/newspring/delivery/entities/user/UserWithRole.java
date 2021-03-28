package com.newspring.delivery.entities.user;

import lombok.Data;

@Data
public class UserWithRole {
  private Long id;
  private String login;
  private Long roleId;
  private String roleName;
}
