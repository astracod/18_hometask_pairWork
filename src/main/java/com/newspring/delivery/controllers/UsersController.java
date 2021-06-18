package com.newspring.delivery.controllers;

import com.newspring.delivery.dto.common.OnlyStatusResponse;
import com.newspring.delivery.dto.options.users.*;
import com.newspring.delivery.entities.user.ChangeRoleOnUser;
import com.newspring.delivery.exceptions.RequestProcessingException;
import com.newspring.delivery.mappers.UserMapper;
import com.newspring.delivery.security.AuthorizationService;
import com.newspring.delivery.services.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("users")
public class UsersController {
    private final UsersService usersService;
    private final UserMapper userMapper;
    private final AuthorizationService authorizationService;

    /**
     * UsersRepository
     *
     * @param user
     * @return
     */
    @PostMapping("registration")
    @PreAuthorize("hasAuthority(3)")
    public OnlyStatusResponse addUser(@RequestBody AddUserRequest user) {
        OnlyStatusResponse response = new OnlyStatusResponse();
        usersService.addUser(userMapper.toUser(user));
        response.setStatus(OnlyStatusResponse.Status.OK);
        return response;
    }


    @PostMapping("change-role")
    @PreAuthorize("hasAuthority(3)")
    public OnlyStatusResponse updateRole(@RequestBody ChangeRoleOnUser updateResponse) {
        OnlyStatusResponse res = new OnlyStatusResponse();
        usersService.updateRole(updateResponse);
        res.setStatus(OnlyStatusResponse.Status.OK);
        return res;
    }

    @GetMapping("roles")
    @PreAuthorize("hasAuthority(3)")
    public GetAllRolesResponse allRolesResponse() {
        return userMapper.toAllRolesResponse(usersService.getAllRoles());
    }



    @GetMapping("part")
    @PreAuthorize("hasAuthority(3)")
    public UserWithRoleResponse allUserWithRole(
            @RequestParam(required = false) Long role,
            @RequestParam(value = "login", required = false) String loginStart
    ) {
        if (role == null && loginStart == null){
            throw new RequestProcessingException("Please enter at least one value");
        }
        return userMapper.toUserWithRoleResponse(usersService.searchUsers(role, loginStart));
    }


    @PostMapping("login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        TokenResponse res = new TokenResponse();
        try {
            String token = authorizationService.login(loginRequest.getLogin(), loginRequest.getPassword());
            res.setStatus("OK");
            res.setToken(token);
        } catch (Exception e) {
            res.setMessages(e.getMessage());
            res.setStatus("Error");
        }
        return res;
    }

}
