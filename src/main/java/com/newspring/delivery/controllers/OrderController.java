package com.newspring.delivery.controllers;

import com.newspring.delivery.dto.common.OnlyStatusResponse;
import com.newspring.delivery.dto.options.orders.AdvanceOrdersResponse;
import com.newspring.delivery.dto.options.orders.CreateOrderDto;
import com.newspring.delivery.entities.order.AdvanceOrder;
import com.newspring.delivery.entities.order.ChangeOrder;
import com.newspring.delivery.entities.order.DeleteOrderRequest;
import com.newspring.delivery.entities.user.User;
import com.newspring.delivery.mappers.OrderMapper;
import com.newspring.delivery.services.OrdersService;
import com.newspring.delivery.services.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("orders")
public class OrderController {
    private final OrdersService ordersService;
    private final UsersService usersService;
    private final OrderMapper orderMapper;

    /**
     * OrderRepository
     * -> требует сериализации Users!!!
     * ВНИМАНИЕ : разобраться с доступом админа ко всем функциям и заказчика к определенным
     *
     * @param name
     * @param description
     * @param address
     * @param minPrice
     * @param maxPrice
     * @return
     */
    @GetMapping("/portion")
    @PreAuthorize("hasAuthority(3)")
    public AdvanceOrdersResponse allAdvancedOrderResponse(@RequestParam(value = "name", required = false) String name,
                                                          @RequestParam(value = "description", required = false) String description,
                                                          @RequestParam(value = "address", required = false) String address,
                                                          @RequestParam(value = "price", required = false) Double minPrice,
                                                          @RequestParam(value = "price", required = false) Double maxPrice) {
        try {
            AdvanceOrdersResponse advanceOrdersResponse = orderMapper.toAdvancedOrders(ordersService.advancedOrderSearch(name, description, address, minPrice, maxPrice));
            returnWithNameAuthor(advanceOrdersResponse);
            return advanceOrdersResponse;
        } catch (Exception e) {
            AdvanceOrdersResponse response = new AdvanceOrdersResponse();
            response.setStatus("ERROR");
            response.setError(e.getMessage());
            log.info("responseException : {}", e.getMessage(), e);
            return response;
        }
    }

    /**
     * внутренниий метод для allAdvancedOrderResponse
     *
     * @param advanceOrdersResponse
     */
    private void returnWithNameAuthor(AdvanceOrdersResponse advanceOrdersResponse) {

        for (AdvanceOrder order : advanceOrdersResponse.getOrders()) {
            Optional<User> user = usersService.getFindById(order.getAuthorId());
            order.setAuthorName(user.map(u -> u.getLogin()).get());
            order.setAuthorPhone(user.map(u -> u.getPhone()).get());
        }
    }

    private Long getUserId() {
        return Long.parseLong(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
    }

    /**
     * OrderRepository
     */
    @PostMapping("/create")
    @PreAuthorize("hasAuthority(1) || hasAuthority(3)")
    public OnlyStatusResponse createOrder(@RequestBody CreateOrderDto createOrderDto) {
        OnlyStatusResponse res = new OnlyStatusResponse();
        try {
            Long userId = getUserId();
            ordersService.createOrder(orderMapper.toCreateOrder(createOrderDto, userId));
            res.setStatus(OnlyStatusResponse.Status.OK);
            res.setMessage(" -> заявка созданна");
        } catch (Exception e) {
            log.error("ERROR create order in Controller {}", e.getMessage(), e);
            res.setStatus(OnlyStatusResponse.Status.FAIL);
            res.setMessage(e.getMessage());
        }
        return res;
    }


    /**
     * OrderRepository
     *
     * @param order
     * @return
     */
    @PutMapping("/change")
    @PreAuthorize("hasAuthority(1) || hasAuthority(3)")
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
    @PreAuthorize("hasAuthority(1) || hasAuthority(3)")
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
