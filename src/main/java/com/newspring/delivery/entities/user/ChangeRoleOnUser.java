package com.newspring.delivery.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeRoleOnUser {
   private Long id;
   private Long roleId;
}
