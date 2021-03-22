package com.newspring.delivery.services;

import com.newspring.delivery.dao.UsersDaoImpl;
import com.newspring.delivery.dto.options_with_user.GetAllRolesResponse;
import com.newspring.delivery.entities.*;
import com.newspring.delivery.exceptions.ValidationException;
import com.newspring.delivery.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {
    private final UsersDaoImpl usersDao;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void addUser(User user) {
        try {
            String hash = passwordEncoder.encode(user.getPassword());
            user.setPassword(hash);
            usersDao.add(user);
        } catch (Exception e) {
            log.error("ERROR ADD USER IN Service {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateRole(ChangeRoleOnUser role) {
        try {
            usersDao.roleChange(role);
        } catch (Exception e) {
            log.error("ERROR UPDATE ROLE IN SERVICE {}", e.getMessage(), e);
        }
    }

    public GetAllRolesResponse getAllRolesResponse() {
        return userMapper.toAllRolesResponse(
                usersDao.getAllRoles()
        );
    }

    public List<UserWithRole> getUserWithRole(Long role, String loginStart) {
        if (loginStart == null && role == null) {
            throw new ValidationException(" Search parameters not passed");
        }
        return usersDao.getAllUsersByRoleAndLoginStart(role, loginStart);
    }

    public void createOrder(Order order) {

        try {
            usersDao.createOrder(order);
        } catch (Exception e) {
            log.info(" Error create order in service {}", e.getMessage(), e);
        }
    }

    public void changeOrder(ChangeOrder order) {
        try {
            usersDao.changeOrder(order);
        } catch (Exception e) {
            log.info(" Error change order in service {}", e.getMessage(), e);
        }
    }

    public void removeOrder(DeleteOrder deleteOrder) {
        try {
            usersDao.remoteOrder(deleteOrder);
        } catch (Exception e) {
            log.info(" Error remove order in service {}", e.getMessage(), e);
        }
    }

    public List<AdvancedOrderResponse> advancedOrderSearch(AdvancedOrder advancedOrder) {
        log.info("service : {}",usersDao.advancedOrderSearch(advancedOrder));
        return usersDao.advancedOrderSearch(advancedOrder);
    }

}
