package com.newspring.delivery.mappers;

import com.newspring.delivery.dto.options.orders.AdvanceOrderFiltersDto;
import com.newspring.delivery.dto.options.orders.AdvanceOrdersResponse;
import com.newspring.delivery.dto.options.orders.AdvancedOrderDto;
import com.newspring.delivery.entities.order.AdvanceOrder;
import com.newspring.delivery.entities.order.AdvanceOrdersFilters;
import com.newspring.delivery.entities.order.ChangeOrder;
import com.newspring.delivery.entities.order.Orders;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
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


    public AdvanceOrder toAdvancedOrderDto(List<Orders> order) {
        AdvanceOrder dto = new AdvanceOrder();
        for (Orders entity : order) {
            dto.setOrderId(entity.getId());
            dto.setPrice(entity.getPrice());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setAddress(entity.getAddress());
        }
        return dto;
    }

    public AdvanceOrdersResponse toAdvancedOrders(List<Orders> orders) {
        AdvanceOrdersResponse res = new AdvanceOrdersResponse();
        res.setStatus("OK");
        res.setOrders(orders.stream().map(o -> toAdvancedOrderDto(Collections.singletonList(o))).collect(Collectors.toList())
        );
        return res;
    }


    public Orders toChangeOrderRequest(ChangeOrder order) {
        Orders orders = new Orders();
        orders.setId(order.getOrderId());
        orders.setName(order.getName());
        orders.setDescription(order.getDescription());
        orders.setAddress(order.getAddress());
        orders.setStatusId(order.getStatusId());
        return orders;
    }
}
