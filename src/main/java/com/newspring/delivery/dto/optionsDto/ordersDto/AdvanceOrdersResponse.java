package com.newspring.delivery.dto.optionsDto.ordersDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceOrdersResponse {
    List<AdvancedOrderDto> orders;
    private String status;
    private String error;
}
