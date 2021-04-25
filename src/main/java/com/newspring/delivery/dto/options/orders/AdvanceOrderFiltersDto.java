package com.newspring.delivery.dto.options.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceOrderFiltersDto {
   private String name;
   private String description;
   private String address;
   private Double minPrice;
   private Double maxPrice;
}
