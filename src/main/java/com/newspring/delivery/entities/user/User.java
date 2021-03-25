package com.newspring.delivery.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private Long id;
  private String login;
  private String password;
  private Long roleId;
  private String phone;
}
