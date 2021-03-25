package com.newspring.delivery.entities.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceOrder {
    private Long orderId;
    private Double price;
    private String name;
    private String description;
    private String address;
}
