package com.newspring.delivery.dao.interfaceDao;


import com.newspring.delivery.entities.user.ChangeRoleOnUser;
import com.newspring.delivery.entities.user.Role;
import com.newspring.delivery.entities.user.User;
import com.newspring.delivery.entities.user.UserWithRole;

import java.util.List;

public interface UsersDao {

    void add(User user);

    void roleChange(ChangeRoleOnUser role);

    List<UserWithRole> getAllUsersByRoleAndLoginStart(Long role, String LoginStart);

    List<Role> getAllRoles();


}
