package com.newspring.delivery.dto.options_with_user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceOrderFiltersDto {
   //private Long orderId;
   private String name;
   private String description;
   private String address;
   private Double minPrice;
   private Double maxPrice;
}
