package com.newspring.delivery.mappers;

import com.newspring.delivery.dto.options.orders.AdvanceOrderFiltersDto;
import com.newspring.delivery.dto.options.orders.AdvanceOrdersResponse;
import com.newspring.delivery.dto.options.orders.CreateOrderDto;
import com.newspring.delivery.entities.order.AdvanceOrder;
import com.newspring.delivery.entities.order.AdvanceOrdersFilters;
import com.newspring.delivery.entities.order.ChangeOrder;
import com.newspring.delivery.entities.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class OrderMapper {

    public AdvanceOrdersFilters toAdvanceOrdersFilters(AdvanceOrderFiltersDto advanceOrderFiltersDto) {
        AdvanceOrdersFilters advanceOrdersFilters = new AdvanceOrdersFilters();
        advanceOrdersFilters.setName(advanceOrderFiltersDto.getName());
        advanceOrdersFilters.setDescription(advanceOrderFiltersDto.getDescription());
        advanceOrdersFilters.setAddress(advanceOrderFiltersDto.getAddress());
        advanceOrdersFilters.setMinPrice(advanceOrderFiltersDto.getMinPrice());
        advanceOrdersFilters.setMaxPrice(advanceOrderFiltersDto.getMaxPrice());
        return advanceOrdersFilters;
    }


    public AdvanceOrder toAdvancedOrderDto(Order order) {
        AdvanceOrder dto = new AdvanceOrder();
        dto.setOrderId(order.getId());
        dto.setAuthorId(order.getAuthorUserId());
        dto.setExecutorId(order.getExecutorUserId());
        dto.setPrice(order.getPrice());
        dto.setName(order.getName());
        dto.setDescription(order.getDescription());
        dto.setAddress(order.getAddress());
        dto.setOrderStatus(order.getOrderStatus().getName());
        return dto;
    }

    public AdvanceOrdersResponse toAdvancedOrders(List<Order> orders) {
        AdvanceOrdersResponse res = new AdvanceOrdersResponse();
        res.setStatus("OK");
        res.setOrders(orders.stream().map(o -> toAdvancedOrderDto(o)).collect(Collectors.toList())
        );
        return res;
    }


    public Order toChangeOrderRequest(ChangeOrder order) {
        Order orders = new Order();
        orders.setId(order.getOrderId());
        orders.setName(order.getName());
        orders.setDescription(order.getDescription());
        orders.setAddress(order.getAddress());
        orders.setStatusId(order.getStatusId());
        return orders;
    }

    /**
     * обратить внимание на поля StatusId,AuthorUserId
     *
     * @param createOrderDto
     * @return
     */
    public Order toCreateOrder(CreateOrderDto createOrderDto) {
        Order order = new Order();
        order.setAuthorUserId(null);
        order.setExecutorUserId(null);
        order.setStatusId(1L);
        order.setName(createOrderDto.getName());
        order.setDescription(createOrderDto.getDescription());
        order.setPrice(createOrderDto.getPrice());
        order.setAddress(createOrderDto.getAddress());
        return order;
    }

    public Order takeOrder(List<Order> orders, Long executorUserId) {
        Order order = new Order();
        for (Order order1 : orders) {
            order.setId(order1.getId());
            order.setAuthorUserId(order1.getAuthorUserId());
            order.setExecutorUserId(executorUserId);
            order.setStatusId(order1.getStatusId());
            order.setName(order1.getName());
            order.setDescription(order1.getDescription());
            order.setPrice(order1.getPrice());
            order.setAddress(order1.getAddress());
        }
        return order;
    }
}
