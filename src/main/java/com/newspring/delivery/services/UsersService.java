package com.newspring.delivery.services;

import com.newspring.delivery.dao.interfaceDao.UsersRepository;
import com.newspring.delivery.entities.user.ChangeRoleOnUser;
import com.newspring.delivery.entities.user.Roles;
import com.newspring.delivery.entities.user.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    /**
     *  UsersRepository
     * @param user
     */
    @Transactional
    public void addUser(Users user) {
        try {
            String hash = passwordEncoder.encode(user.getPassword());
            user.setPassword(hash);
            usersRepository.save(user);
        } catch (Exception e) {
            log.error("ERROR ADD USER IN Service {}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *  UsersRepository
     * @param role
     */
    public void updateRole(ChangeRoleOnUser role) {
        try {
            usersRepository.roleChange(role.getId(), role.getRoleId());
        } catch (Exception e) {
            log.error("ERROR UPDATE ROLE IN SERVICE {}", e.getMessage(), e);
        }
    }


    @Transactional
    public List<Roles> getAllRolesResponse() {
        return usersRepository.getAllRoles();
    }


    @Transactional
    public List<Users> getUserWithRole(Long role, String loginStart) {
        return usersRepository.getAllUsersByRoleAndLoginStart(role, loginStart);
    }

}
