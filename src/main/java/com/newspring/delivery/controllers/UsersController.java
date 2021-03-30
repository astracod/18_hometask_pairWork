package com.newspring.delivery.controllers;

import com.newspring.delivery.dto.common.OnlyStatusResponse;
import com.newspring.delivery.dto.optionsDto.usersDto.*;
import com.newspring.delivery.entities.user.ChangeRoleOnUser;
import com.newspring.delivery.entities.user.UserFromTokenAfterChecking;
import com.newspring.delivery.mappers.UserMapper;
import com.newspring.delivery.services.UsersService;
import com.newspring.delivery.token.JwtImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("users")
public class UsersController {
    private final UsersService usersService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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

    @GetMapping("/token")
    public LoginForTokenResponse getUser(LoginRequestDto loginRequestDto) {
        try {
            List<UserFromTokenAfterChecking> u = usersService.getUser(
                    userMapper.toUserToken(loginRequestDto));


            boolean asc = usersService.validationСheck(u, loginRequestDto);
            if (asc) {
                return userMapper.toJwtTokenResponse(
                        usersService.getUser(
                                userMapper.toUserToken(loginRequestDto)));

            } else {
                LoginForTokenResponse login = new LoginForTokenResponse();
                login.setMessages(" There is no such user");
                return login;
            }
        } catch (Exception e) {
            log.error("UserController {}", e.getMessage(), e);
            LoginForTokenResponse login = new LoginForTokenResponse();
            login.setMessages("ERROR in UserController");
            return login;
        }
    }

}
// Запросы for Bash:
// 1) curl -XPOST http://localhost:8080/users/add -H"Content-Type:application/json" -d'{"login":"Dima","password":"369852","roleId":2,"phone":"222111"}'
// 2) curl -XGET http://localhost:8080/users/roles
// Запросы в идеи
// 4) curl -XPOST http://localhost:8080/users/up -H"Content-Type:application/json" -d"{\"id\":2,\"roleId\":2}" для консоли идеи.Разница во внешних кавычках.
//  запросы из браузера
// 3)  http://localhost:8080/users/part?role=2&login=A
// http://localhost:8080/users/token?login=Dmitriy&pass=$2a$10$/ctpKbv6wEOE1NgHbPEFnuuvackBy/nXbQie2Gslf5U7wrySoGwxO