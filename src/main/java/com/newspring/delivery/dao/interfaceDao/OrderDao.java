package com.newspring.delivery.dao.interfaceDao;

import com.newspring.delivery.entities.order.*;

import java.util.List;

public interface OrderDao {
    void createOrder(Order order);

    void changeOrder(ChangeOrder changeOrder);

    List<AdvanceOrder> advancedOrderSearch(AdvanceOrdersFilters advancedOrder);

    void removeOrder(DeleteOrderRequest deleteOrder);
}
