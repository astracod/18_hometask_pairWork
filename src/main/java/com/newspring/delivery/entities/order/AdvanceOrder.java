package com.newspring.delivery.entities.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdvanceOrder {
    private Long orderId;
    private Long authorId;
    private String authorName;
    private String authorPhone;
    private Long executorId;
    private String executorName;
    private String executorPhone;
    private String OrderStatus;
    private Double price;
    private String name;
    private String description;
    private String address;
}
