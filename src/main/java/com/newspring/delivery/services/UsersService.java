package com.newspring.delivery.services;

import com.newspring.delivery.dao.UsersDaoImpl;
import com.newspring.delivery.dto.options_with_user.GetAllRolesResponse;
import com.newspring.delivery.entities.ChangeRoleOnUser;
import com.newspring.delivery.entities.User;
import com.newspring.delivery.entities.UserWithRole;
import com.newspring.delivery.exceptions.ValidationException;
import com.newspring.delivery.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {
    private final UsersDaoImpl usersDao;
    private final UserMapper userMapper;


    public void addUserDao(User user) {
        try {
            usersDao.add(user);
        } catch (Exception e) {
            log.error("ERROR ADD USER IN Service {}", e.getMessage());
            e.printStackTrace();
        }
    }
    public void updateRole(ChangeRoleOnUser role){
        try{
            usersDao.roleChange(role);
        }catch (Exception e){
            log.error("ERROR UPDATE ROLE IN SERVICE {}", e.getMessage(),e);
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

}
