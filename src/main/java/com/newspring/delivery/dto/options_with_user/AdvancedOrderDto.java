package com.newspring.delivery.dto.options_with_user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvancedOrderDto {
    private Long orderId;
    private Double price;
    private String name;
    private String description;
    private String address;
}
