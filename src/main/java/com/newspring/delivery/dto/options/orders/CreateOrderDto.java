package com.newspring.delivery.dto.options.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {
  private String name;
  private String description;
  private Double price;
  private String address;
}
