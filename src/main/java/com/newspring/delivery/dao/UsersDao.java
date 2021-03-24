package com.newspring.delivery.dao;

import com.newspring.delivery.dto.options_with_user.AdvancedOrderDto;
import com.newspring.delivery.entities.*;

import java.util.List;

public interface UsersDao {
    void createOrder(Order order);

    void changeOrder(ChangeOrder changeOrder);

    List<AdvancedOrderDto> advancedOrderSearch(AdvancedOrder advancedOrder);

    void removeOrder(DeleteOrderRequest deleteOrder);

    void add(User user);

    void roleChange(ChangeRoleOnUser role);

    List<UserWithRole> getAllUsersByRoleAndLoginStart(Long role, String LoginStart);

    List<Role> getAllRoles();
}
