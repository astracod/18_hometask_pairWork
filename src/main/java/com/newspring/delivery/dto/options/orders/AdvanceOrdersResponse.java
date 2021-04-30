package com.newspring.delivery.dto.options.orders;


import com.newspring.delivery.entities.order.AdvanceOrder;
import com.newspring.delivery.entities.order.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceOrdersResponse {
    List<AdvanceOrder> orders;
    private String status;
    private String error;
}
