package com.newspring.delivery.controllers;

import com.newspring.delivery.dao.interfaceDao.UsersRepository;
import com.newspring.delivery.dto.common.OnlyStatusResponse;
import com.newspring.delivery.dto.options.users.*;
import com.newspring.delivery.entities.user.ChangeRoleOnUser;
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
    private final UsersRepository usersRepository;

    /**
     * UsersRepository
     *
     * @param user
     * @return
     */
    @PostMapping("/registration")
    @PreAuthorize("hasAuthority(3)")
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

    /**
     * UsersRepository
     *
     * @param updateResponse
     * @return
     */
    @PostMapping("/up")
    @PreAuthorize("hasAuthority(3)")
    public OnlyStatusResponse updateRole(@RequestBody ChangeRoleOnUser updateResponse) {
        OnlyStatusResponse res = new OnlyStatusResponse();
        try {
            usersRepository.roleChange(updateResponse.getId(), updateResponse.getRoleId());
            res.setStatus(OnlyStatusResponse.Status.OK);
            res.setMessage(" -> обновление произведено");
        } catch (Exception e) {
            log.error("ERROR UPDATE USER in Controller {}", e.getMessage(), e);
            res.setStatus(OnlyStatusResponse.Status.FAIL);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    /**
     * UsersRepository, JpaRepository<Roles,Long>
     *
     * @return
     */
    @GetMapping("/roles")
    @PreAuthorize("hasAuthority(3)")
    public GetAllRolesResponse allRolesResponse() {
        try {
            return userMapper.toAllRolesResponse(usersRepository.getAllRoles());
        } catch (Exception e) {
            log.error("ERROR IN UsersController {}", e.getMessage(), e);
            GetAllRolesResponse response = new GetAllRolesResponse();
            response.setStatus("ERROR");
            response.setError(e.getMessage());
            return response;
        }
    }


    /**
     * UsersRepository
     *
     * @param role
     * @param loginStart
     * @return
     */
    @GetMapping("/part")
    @PreAuthorize("hasAuthority(3)")
    public UserWithRoleResponse allUserWithRole(
            @RequestParam(required = false) Long role,
            @RequestParam(value = "login", required = false) String loginStart
    ) {
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

    /**
     * UsersRepository
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public TokenResponse getUser(@RequestBody LoginRequest loginRequest) {
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
