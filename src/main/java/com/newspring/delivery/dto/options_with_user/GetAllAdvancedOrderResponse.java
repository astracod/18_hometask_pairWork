package com.newspring.delivery.dto.options_with_user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAdvancedOrderResponse {
    List<AdvancedOrderDto> orders;
    private String status;
    private String error;
}
