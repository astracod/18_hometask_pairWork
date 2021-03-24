package com.newspring.delivery.entities;

import lombok.Data;

@Data
public class AdvanceOrdersFilters {
    //private Long orderId;
    private String name;
    private String description;
    private String address;
    private Double minPrice;
    private Double maxPrice;
}
