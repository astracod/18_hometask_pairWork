package com.newspring.delivery.services;

import com.newspring.delivery.dao.interfaceDao.OrderRepository;
import com.newspring.delivery.entities.order.DeleteOrderRequest;
import com.newspring.delivery.entities.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {

    private final OrderRepository orderRepository;

    public void changeOrder(Order order) {
        try {
            orderRepository.changeOrder(order.getName(), order.getDescription(), order.getAddress(), order.getId());
        } catch (Exception e) {
            log.info(" Error change order in service {}", e.getMessage(), e);
        }
    }

    @Transactional
    @Modifying
    public void removeOrder(DeleteOrderRequest deleteOrder) {
        try {
            Long id = deleteOrder.getOrderId();
            orderRepository.deleteById(id);

        } catch (Exception e) {
            log.info(" Error remove order in service {}", e.getMessage(), e);
        }
    }

    public List<Order> advancedOrderSearch(String name, String description, String address, Double minPrice, Double maxPrice) {
        return orderRepository.advancedOrderSearch(name, description, address, minPrice, maxPrice);
    }

    @Transactional
    public void createOrder(Order order) {
        try {
            orderRepository.save(order);
        } catch (Exception e) {
            log.info(" Error create order in service {}", e.getMessage(), e);
        }
    }
}
