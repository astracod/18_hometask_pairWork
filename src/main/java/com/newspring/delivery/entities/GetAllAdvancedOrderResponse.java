package com.newspring.delivery.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAdvancedOrderResponse {
    List<AdvancedOrderResponse> orders;
    private String status;
    private String error;
}
