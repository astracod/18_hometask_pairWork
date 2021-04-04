package com.newspring.delivery.mappers;

import com.newspring.delivery.dto.options.orders.AdvanceOrderFiltersDto;
import com.newspring.delivery.dto.options.orders.AdvanceOrdersResponse;
import com.newspring.delivery.dto.options.orders.AdvancedOrderDto;
import com.newspring.delivery.entities.order.AdvanceOrder;
import com.newspring.delivery.entities.order.AdvanceOrdersFilters;
import org.springframework.stereotype.Component;

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


    public AdvancedOrderDto toAdvancedOrderDto(AdvanceOrder advanceOrders) {
        AdvancedOrderDto dto = new AdvancedOrderDto();
        dto.setOrderId(advanceOrders.getOrderId());
        dto.setPrice(advanceOrders.getPrice());
        dto.setName(advanceOrders.getName());
        dto.setDescription(advanceOrders.getDescription());
        dto.setAddress(advanceOrders.getAddress());
        return dto;
    }

    public AdvanceOrdersResponse toAdvancedOrders(List<AdvanceOrder> orders) {
        AdvanceOrdersResponse res = new AdvanceOrdersResponse();
        res.setStatus("OK");
        res.setOrders(orders.stream().map(o -> toAdvancedOrderDto(o)).collect(Collectors.toList())
        );
        return res;
    }
}
