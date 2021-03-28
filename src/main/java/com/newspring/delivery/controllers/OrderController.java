package com.newspring.delivery.controllers;

import com.newspring.delivery.dto.common.OnlyStatusResponse;
import com.newspring.delivery.dto.optionsDto.ordersDto.AdvanceOrderFiltersDto;
import com.newspring.delivery.dto.optionsDto.ordersDto.AdvanceOrdersResponse;
import com.newspring.delivery.entities.order.ChangeOrder;
import com.newspring.delivery.entities.order.DeleteOrderRequest;
import com.newspring.delivery.entities.order.Order;
import com.newspring.delivery.mappers.OrderMapper;
import com.newspring.delivery.services.OrdersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("orders")
public class OrderController {
    private final OrdersService ordersService;
    private final OrderMapper orderMapper;


    @GetMapping("/portion")
    public AdvanceOrdersResponse allAdvancedOrderResponse(AdvanceOrderFiltersDto advancedOrder) {
        try {
            log.info("response : {}",
                    orderMapper.toAdvancedOrders(ordersService.advancedOrderSearch(orderMapper.toAdvanceOrdersFilters(advancedOrder)))
            );
            return orderMapper.toAdvancedOrders(ordersService.advancedOrderSearch(orderMapper.toAdvanceOrdersFilters(advancedOrder)));
        } catch (Exception e) {
            AdvanceOrdersResponse response = new AdvanceOrdersResponse();
            response.setStatus("ERROR");
            response.setError(e.getMessage());
            log.info("response : {}", e.getMessage(), e);
            return response;
        }
    }


    @PostMapping("/create")
    public OnlyStatusResponse createOrder(@RequestBody Order order) {
        OnlyStatusResponse res = new OnlyStatusResponse();
        try {
            ordersService.createOrder(order);
            res.setStatus(OnlyStatusResponse.Status.OK);
            res.setMessage(" -> заявка созданна");
        } catch (Exception e) {
            log.error("ERROR create order in Controller {}", e.getMessage(), e);
            res.setStatus(OnlyStatusResponse.Status.FAIL);
            res.setMessage(e.getMessage());
        }
        return res;
    }


    @PutMapping("/change")
    public OnlyStatusResponse ChangeOrderRequest(@RequestBody ChangeOrder order) {
        OnlyStatusResponse res = new OnlyStatusResponse();
        try {
            ordersService.changeOrder(order);
            res.setStatus(OnlyStatusResponse.Status.OK);
            res.setMessage(" -> заявка обновлена");
        } catch (Exception e) {
            log.error("ERROR change order in Controller {}", e.getMessage(), e);
            res.setStatus(OnlyStatusResponse.Status.FAIL);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @DeleteMapping("/remove")
    public OnlyStatusResponse removeOrder(@RequestBody DeleteOrderRequest deleteOrder) {
        OnlyStatusResponse res = new OnlyStatusResponse();
        try {
            ordersService.removeOrder(deleteOrder);
            res.setStatus(OnlyStatusResponse.Status.OK);
            res.setMessage(" -> заявка удалена");
        } catch (Exception e) {
            log.error("ERROR remove order in Controller {}", e.getMessage(), e);
            res.setStatus(OnlyStatusResponse.Status.FAIL);
            res.setMessage(e.getMessage());
        }
        return res;
    }

 // 8)  http://localhost:8080/orders/portion?name=ffe&description=ack&address=Kislo
 // 7) curl -XDELETE http://localhost:8080/orders/remove -H "Content-Type:application/json" -d"{\"orderId\":5}"
 // 6) curl -XPOST http://localhost:8080/orders/change -H"Content-Type:application/json" -d"{\"authorUserId\":2,\"name\":\"Milk\",\"description\":\"white milk\",\"address\":\"Kislovodsk, Vernadsky 5\",\"statusId\":1}"
 // 5) curl -XPOST http://localhost:8080/orders/create -H"Content-Type:application/json" -d"{\"authorUserId\":1,\"executorUserId\":null,\"price\":150,\"name\":\"Marlboro Gold\",\"description\":\"medium nicotine cigarettes\",\"address\":\"Essentuki, Kalinina 2\",\"statusId\":1}"
}
