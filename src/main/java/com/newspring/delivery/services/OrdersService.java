package com.newspring.delivery.services;

import com.newspring.delivery.dao.implementationDao.OrderDaoImpl;
import com.newspring.delivery.entities.order.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {
    private final OrderDaoImpl orderDao;

    public void changeOrder(ChangeOrder order) {
        try {
            orderDao.changeOrder(order);
        } catch (Exception e) {
            log.info(" Error change order in service {}", e.getMessage(), e);
        }
    }

    public void removeOrder(DeleteOrderRequest deleteOrder) {
        try {
            orderDao.removeOrder(deleteOrder);
        } catch (Exception e) {
            log.info(" Error remove order in service {}", e.getMessage(), e);
        }
    }

    public List<AdvanceOrder> advancedOrderSearch(AdvanceOrdersFilters advancedOrder) {
        log.info("service : {}", orderDao.advancedOrderSearch(advancedOrder));
        return orderDao.advancedOrderSearch(advancedOrder);
    }

    public void createOrder(Order order) {
        try {
            orderDao.createOrder(order);
        } catch (Exception e) {
            log.info(" Error create order in service {}", e.getMessage(), e);
        }
    }
}
