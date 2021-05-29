package com.newspring.delivery.services;

import com.newspring.delivery.dao.interfaceDao.OrderRepository;
import com.newspring.delivery.entities.order.DeleteOrderRequest;
import com.newspring.delivery.entities.order.Order;
import com.newspring.delivery.entities.order.OrderStatus;
import com.newspring.delivery.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

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
        Optional<Order> order = orderRepository.findById(deleteOrder.getOrderId());
        Long statusId = order.get().getStatusId();
        List<Long> authority = getUserAuthorities();
        for (Long authorityUsers : authority) {
            if ( authorityUsers == 3  || (statusId == 1 && authorityUsers == 1)  ) {

                try {
                    Long id = deleteOrder.getOrderId();
                    orderRepository.deleteById(id);

                } catch (Exception e) {
                    log.info(" Error remove order in service {}", e.getMessage(), e);
                }
            }
        }
    }

    @Transactional
    public List<Order> advancedOrderSearch(String name, String description, String address, Double minPrice, Double maxPrice) {
        List<Order> orders = orderRepository.advancedOrderSearch(name, description, address, minPrice, maxPrice);
        List<OrderStatus> list = orders.stream().map(Order::getOrderStatus).collect(Collectors.toList());
        for (OrderStatus orderStatus : list) {
            for (Order order : orders) {
                if (order.getStatusId().equals(orderStatus.getId())) order.setOrderStatus(order.getOrderStatus());
            }
        }
        return orders;
    }

    @Transactional
    public void createOrder(Order order) {
        Long userId = getUserId();
        try {
            order.setAuthorUserId(userId);
            orderRepository.save(order);
        } catch (Exception e) {
            log.info(" Error create order in service {}", e.getMessage(), e);
        }
    }

    public List<Order> returnAllNonExecutableOrders() {
        return orderRepository.getAllOrdersWhereStatusIdOne();
    }


    @Transactional
    @Modifying
    public void takeOrderToWork(Long orderId) {
        Long executorUserId = getUserId();
        List<Long> userAuthorities = getUserAuthorities();
        try {
            Order order = orderMapper.takeOrder(orderRepository.findByOrderId(orderId), executorUserId);
            userAuthorities.forEach(authority -> order.setStatusId(changeStatusId(order.getStatusId(), authority)));
            orderRepository.save(order);
        } catch (Exception e) {
            log.info(" Error remove order in service {}", e.getMessage(), e);
        }
    }


    /**
     * используется для определения id  пользователя
     *
     * @return
     */
    private Long getUserId() {
        return Long.parseLong(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
    }

    /**
     * получение листа аутентификации пробное использование в методе takeOrderToWork     *
     *
     * @return
     */
    private List<Long> getUserAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    /**
     * ВНИМАНИЕ
     * внутренний метод для takeOrderToWork
     *
     * @param id
     * @param authority
     * @return
     */
    private Long changeStatusId(Long id, Long authority) {
        if (authority == 3) {
            id = 5L;
        } else if (authority == 2 && (id < 3)) {
            id += 1;
        } else if (authority == 1 && id == 3) {
            id = 4L;
        }
        return id;
    }
}
