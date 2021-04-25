package com.newspring.delivery.dao.interfaceDao;


import com.newspring.delivery.entities.user.*;

import java.util.List;

public interface UsersDao {

    void add(User user);

    void roleChange(ChangeRoleOnUser role);

    List<UserWithRole> getAllUsersByRoleAndLoginStart(Long role, String LoginStart);

    List<Role> getAllRoles();

    User findByLogin(String login);
}
