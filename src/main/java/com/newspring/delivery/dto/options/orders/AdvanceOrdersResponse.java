package com.newspring.delivery.dto.options.orders;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.newspring.delivery.entities.order.AdvanceOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdvanceOrdersResponse {
    List<AdvanceOrder> orders;
    private String status;
    private String error;
}
