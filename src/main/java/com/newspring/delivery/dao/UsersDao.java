package com.newspring.delivery.dao;

import com.newspring.delivery.entities.*;

import java.util.List;

public interface UsersDao {
    void createOrder(Order order);

    void changeOrder(ChangeOrder changeOrder);

    List<AdvancedOrderResponse> advancedOrderSearch(AdvancedOrder advancedOrder);

    void remoteOrder(DeleteOrder deleteOrder);

    void add(User user);

    void roleChange(ChangeRoleOnUser role);

    List<UserWithRole> getAllUsersByRoleAndLoginStart(Long role, String LoginStart);

    List<Role> getAllRoles();
}
