package com.newspring.delivery.controllers;

import com.newspring.delivery.dto.common.OnlyStatusResponse;
import com.newspring.delivery.dto.options_with_user.*;
import com.newspring.delivery.entities.*;
import com.newspring.delivery.mappers.UserMapper;
import com.newspring.delivery.services.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("users")
public class UsersController {
    private final UsersService usersService;
    private final UserMapper userMapper;

    @PostMapping("/add")
    public OnlyStatusResponse addUser(@RequestBody AddUserRequest user) {

        OnlyStatusResponse response = new OnlyStatusResponse();
        try {
            usersService.addUser(userMapper.toUser(user));
            response.setStatus(OnlyStatusResponse.Status.OK);
            response.setMessage(" -> добавление произведено");
        } catch (Exception e) {
            log.error("ERROR ADD USER in Controller {}", e.getMessage(), e);
            response.setStatus(OnlyStatusResponse.Status.FAIL);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/up")
    public OnlyStatusResponse updateRole(@RequestBody ChangeRoleOnUser updateResponse) {
        OnlyStatusResponse res = new OnlyStatusResponse();
        try {
            usersService.updateRole(updateResponse);
            res.setStatus(OnlyStatusResponse.Status.OK);
            res.setMessage(" -> обновление произведено");
        } catch (Exception e) {
            log.error("ERROR UPDATE USER in Controller {}", e.getMessage(), e);
            res.setStatus(OnlyStatusResponse.Status.FAIL);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @GetMapping("/roles")
    public GetAllRolesResponse allRolesResponse() {
        try {
            return userMapper.toAllRolesResponse(usersService.getAllRolesResponse());
        } catch (Exception e) {
            log.error("ERROR IN UsersController {}", e.getMessage(), e);
            GetAllRolesResponse response = new GetAllRolesResponse();
            response.setStatus("ERROR");
            response.setError(e.getMessage());
            return response;
        }
    }

    @GetMapping("/portion")
    public GetAllAdvancedOrderResponse allAdvancedOrderResponse(AdvancedOrder advancedOrder) {
        try {
            log.info("response : {}", userMapper.toAdvancedOrders(usersService.advancedOrderSearch(advancedOrder)));
            return userMapper.toAdvancedOrders(usersService.advancedOrderSearch(advancedOrder));
        } catch (Exception e) {
            GetAllAdvancedOrderResponse response = new GetAllAdvancedOrderResponse();
            response.setStatus("ERROR");
            response.setError(e.getMessage());
            log.info("response : {}", e.getMessage(), e);
            return response;
        }
    }

    @GetMapping("/part")
    public UserWithRoleResponse allUserWithRole(
            @RequestParam(required = false) Long role,
            @RequestParam(value = "login", required = false) String loginStart
    ) {
        log.info("role {}, login {} ", role, loginStart);
        try {
            return userMapper.toUserWithRoleResponse(usersService.getUserWithRole(role, loginStart));
        } catch (Exception e) {
            log.error("ERROR in UsersController , allUserWithRole", e.getMessage(), e);
            UserWithRoleResponse response = new UserWithRoleResponse();
            response.setStatus("ERROR. Search parameters not passed");
            response.setError(e.getMessage());
            return response;
        }
    }


    @PostMapping("/create")
    public OnlyStatusResponse createOrder(@RequestBody Order order) {
        OnlyStatusResponse res = new OnlyStatusResponse();
        try {
            usersService.createOrder(order);
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
            usersService.changeOrder(order);
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
            usersService.removeOrder(deleteOrder);
            res.setStatus(OnlyStatusResponse.Status.OK);
            res.setMessage(" -> заявка удалена");
        } catch (Exception e) {
            log.error("ERROR remove order in Controller {}", e.getMessage(), e);
            res.setStatus(OnlyStatusResponse.Status.FAIL);
            res.setMessage(e.getMessage());
        }
        return res;
    }
}
// Запросы for Bash:
// 1) curl -XPOST http://localhost:8080/users/add -H"Content-Type:application/json" -d'{"login":"Dima","password":"369852","roleId":2,"phone":"222111"}'
// 2) curl -XGET http://localhost:8080/users/roles
// Запросы в идеи
// 4) curl -XPOST http://localhost:8080/users/up -H"Content-Type:application/json" -d"{\"id\":2,\"roleId\":2}" для консоли идеи.Разница во внешних кавычках.
// 5) curl -XPOST http://localhost:8080/users/create -H"Content-Type:application/json" -d"{\"authorUserId\":1,\"executorUserId\":null,\"price\":150,\"name\":\"Marlboro Gold\",\"description\":\"medium nicotine cigarettes\",\"address\":\"Essentuki, Kalinina 2\",\"statusId\":1}"
// 6) curl -XPOST http://localhost:8080/users/change -H"Content-Type:application/json" -d"{\"authorUserId\":2,\"name\":\"Milk\",\"description\":\"white milk\",\"address\":\"Kislovodsk, Vernadsky 5\",\"statusId\":1}"
// 7) curl -XDELETE http://localhost:8080/users/remove -H "Content-Type:application/json" -d"{\"orderId\":5}"
//  запросы из браузера
// 3)  http://localhost:8080/users/part?role=2&login=A
// 8)  http://localhost:8080/users/portion?name=ffe&description=ack&address=Kislo