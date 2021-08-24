package com.newspring.delivery.services;

import com.newspring.delivery.dao.interfaceDao.OrderRepository;
import com.newspring.delivery.dao.interfaceDao.UsersRepository;
import com.newspring.delivery.entities.order.ChangeOrder;
import com.newspring.delivery.entities.order.DeleteOrderRequest;
import com.newspring.delivery.entities.order.Order;
import com.newspring.delivery.entities.order.OrderStatus;
import com.newspring.delivery.entities.user.User;
import com.newspring.delivery.exceptions.RequestProcessingException;
import com.newspring.delivery.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {

    private final OrderRepository orderRepository;
    private final UsersRepository usersRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public void changeOrder(ChangeOrder orderChange) {

        Order order = orderRepository.findById(orderChange.getOrderId())
                .orElseThrow(() -> new RequestProcessingException("Order not found"));
        if (order.getOrderStatus().getId() == 1) {
            order.setName(orderChange.getName());
            order.setDescription(orderChange.getDescription());
            order.setAddress(orderChange.getAddress());
        }

    }

    @Transactional
    @Modifying
    public void removeOrder(DeleteOrderRequest deleteOrder) {
        Optional<Order> order = orderRepository.findById(deleteOrder.getOrderId());
        Long statusId = order.get().getOrderStatus().getId();
        List<Long> authority = getUserAuthorities();
        for (Long authorityUsers : authority) {
            if (authorityUsers == 3 || (statusId == 1 && authorityUsers == 1)) {

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
                if (order.getOrderStatus().getId().equals(orderStatus.getId())) {
                    order.setOrderStatus(orderStatus);
                }
            }
        }
        return orders;
    }

    @Transactional
    public void createOrder(Order order) {
        Long userId = getUserId();
        User user = new User();
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(1L);
        user.setId(userId);
        try {
            order.setCustomer(user);
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
        } catch (Exception e) {
            log.info(" Error create order in service {}", e.getMessage(), e);
        }
    }

    @Transactional
    public List<Order> returnAllNonExecutableOrders() {
        return orderRepository.getAllOrdersWhereStatusIdOne();
    }


    @Transactional
    @Modifying
    public void takeOrderToWork(Long orderId) {
        Long executorUserId = getUserId();
        List<Long> userAuthorities = getUserAuthorities();
        OrderStatus orderStatus = new OrderStatus();

        Order order = orderMapper.takeOrder(Collections.singletonList(orderRepository.findById(orderId).get()));
        Optional<User> executorUser = usersRepository.findById(executorUserId);

        try {
            for (Long userAuthority : userAuthorities) {
                orderStatus.setId(changeStatusId(order.getOrderStatus().getId(), userAuthority));
            }
            order.setOrderStatus(orderStatus);
            order.setExecutor(executorUser.get());
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
