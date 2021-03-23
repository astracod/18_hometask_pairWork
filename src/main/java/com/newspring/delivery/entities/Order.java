package com.newspring.delivery.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  private Long orderId;
  private Long authorUserId;
  private Long executorUserId;
  private Double price;
  private String name;
  private String description;
  private String address;
  private Long statusId;
}
