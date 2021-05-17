package com.newspring.delivery.entities.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceOrder {
    private Long orderId;
    private Long authorId;
    private String authorName;
    private String authorPhone;
    private Double price;
    private String name;
    private String description;
    private String address;
}
