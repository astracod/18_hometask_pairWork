package com.newspring.delivery.services;

import com.newspring.delivery.dao.implementationDao.UsersDaoImpl;
import com.newspring.delivery.dto.optionsDto.usersDto.LoginRequestDto;
import com.newspring.delivery.entities.user.*;
import com.newspring.delivery.exceptions.ValidationException;

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

    public List<Role> getAllRolesResponse() {
        return usersDao.getAllRoles();
    }

    public List<UserWithRole> getUserWithRole(Long role, String loginStart) {
        if (loginStart == null && role == null) {
            throw new ValidationException(" Search parameters not passed");
        }
        return usersDao.getAllUsersByRoleAndLoginStart(role, loginStart);
    }

    public List<UserFromTokenAfterChecking> getUser(UserFromToken userFromToken) {
        return usersDao.fetchByLogin(userFromToken);
    }

    public boolean validationСheck(List<UserFromTokenAfterChecking> u, LoginRequestDto loginRequestDto) {
        String ab = loginRequestDto.getPass();
        String ac = "";
        for (UserFromTokenAfterChecking user : u) {
            ac = user.getPass();
        }
        return passwordEncoder.matches(ab, ac);
    }
}
