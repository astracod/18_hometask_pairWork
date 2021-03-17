package com.newspring.delivery.dao;

import com.newspring.delivery.entities.ChangeRoleOnUser;
import com.newspring.delivery.entities.Role;
import com.newspring.delivery.entities.User;
import com.newspring.delivery.entities.UserWithRole;

import java.util.List;

public interface UsersDao {

    List<User> getAll();

    void add(User user);

    void roleChange(ChangeRoleOnUser role);

    List<UserWithRole> getAllUsersByRoleAndLoginStart(Long role, String LoginStart);

    List<Role> getAllRoles();
}
