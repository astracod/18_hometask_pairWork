package com.newspring.delivery.controllers;

import com.newspring.delivery.dto.common.OnlyStatusResponse;
import com.newspring.delivery.dto.options.orders.AdvanceOrdersResponse;
import com.newspring.delivery.dto.options.orders.CreateOrderDto;
import com.newspring.delivery.entities.order.ChangeOrder;
import com.newspring.delivery.entities.order.DeleteOrderRequest;
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

    /**
     * OrderRepository
     * -> требует сериализации Users!!!
     *
     * @param name
     * @param description
     * @param address
     * @param minPrice
     * @param maxPrice
     * @return
     */
    @GetMapping("/portion")
    public AdvanceOrdersResponse allAdvancedOrderResponse(@RequestParam(value = "name", required = false) String name,
                                                          @RequestParam(value = "description", required = false) String description,
                                                          @RequestParam(value = "address", required = false) String address,
                                                          @RequestParam(value = "price", required = false) Double minPrice,
                                                          @RequestParam(value = "price", required = false) Double maxPrice) {
        try {
            log.info("OrderControllerResponse : {}",
                    orderMapper.toAdvancedOrders(ordersService.advancedOrderSearch(name, description, address, minPrice, maxPrice))
            );
            return orderMapper.toAdvancedOrders(ordersService.advancedOrderSearch(name, description, address, minPrice, maxPrice));
        } catch (Exception e) {
            AdvanceOrdersResponse response = new AdvanceOrdersResponse();
            response.setStatus("ERROR");
            response.setError(e.getMessage());
            log.info("responseException : {}", e.getMessage(), e);
            return response;
        }
    }

    /**
     * OrderRepository
     */
    @PostMapping("/create")
    public OnlyStatusResponse createOrder(@RequestBody CreateOrderDto createOrderDto) {
        OnlyStatusResponse res = new OnlyStatusResponse();
        try {
            ordersService.createOrder(orderMapper.toCreateOrder(createOrderDto));
            res.setStatus(OnlyStatusResponse.Status.OK);
            res.setMessage(" -> заявка созданна");
        } catch (Exception e) {
            log.error("ERROR create order in Controller {}", e.getMessage(), e);
            res.setStatus(OnlyStatusResponse.Status.FAIL);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    /*{"authorUserId":10,"executorUserId":null,"price":420,"name":"Presidentti Original","description":"black coffee","address":"Lermontov, Mira 36","statusId":2}*/

    /**
     * OrderRepository
     *
     * @param order
     * @return
     */
    @PutMapping("/change")
    public OnlyStatusResponse ChangeOrderRequest(@RequestBody ChangeOrder order) {
        OnlyStatusResponse res = new OnlyStatusResponse();
        try {
            ordersService.changeOrder(orderMapper.toChangeOrderRequest(order));
            res.setStatus(OnlyStatusResponse.Status.OK);
            res.setMessage(" -> заявка обновлена");
        } catch (Exception e) {
            log.error("ERROR change order in Controller {}", e.getMessage(), e);
            res.setStatus(OnlyStatusResponse.Status.FAIL);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    /**
     * OrderRepository
     *
     * @param deleteOrder
     * @return
     */
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
