package com.newspring.delivery.services;

import com.newspring.delivery.dao.interfaceDao.RolesRepository;
import com.newspring.delivery.dao.interfaceDao.UsersRepository;
import com.newspring.delivery.domains.UserRoles;
import com.newspring.delivery.entities.user.ChangeRoleOnUser;
import com.newspring.delivery.entities.user.Role;
import com.newspring.delivery.entities.user.User;
import com.newspring.delivery.exceptions.RequestProcessingException;
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
    private final RolesRepository rolesRepository;

    /**
     * UsersRepository
     *
     * @param user
     */
    @Transactional
    public void addUser(User user) {
        try {
            Role customerRole = rolesRepository.findById(UserRoles.CUSTOMER.getId())
                    .orElseThrow(() -> new RequestProcessingException("Role not found"));
            String hash = passwordEncoder.encode(user.getPassword());
            user.setRole(customerRole);
            user.setPassword(hash);
            usersRepository.save(user);
        }catch (RequestProcessingException ex){
            throw ex;
        }catch (Exception e) {
            throw new RequestProcessingException("Registration failed");
        }
    }

    /**
     * UsersRepository
     *
     * @param roleChange
     */
    @Transactional
    public void updateRole(ChangeRoleOnUser roleChange) {
        User user = usersRepository.findById(roleChange.getId())
                .orElseThrow(() -> new RequestProcessingException("User not found"));

        Role role = rolesRepository.findById(roleChange.getRoleId())
                .orElseThrow(() -> new RequestProcessingException("Role not found"));

        user.setRole(role);
    }


    @Transactional
    public List<Role> getAllRoles() {
        return rolesRepository.findAll();
    }


    @Transactional
    public List<User> searchUsers(Long role, String loginStart) {
        return usersRepository.getAllUsersByRoleAndLoginStart(role, loginStart);
    }

    @Transactional
    public User getFindById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }
}
