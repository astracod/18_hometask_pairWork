package com.newspring.delivery.dao.interfaceDao;

import com.newspring.delivery.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UsersRepository extends JpaRepository<User, Long> {



    User findByLogin(String login);


    @Query(value = "select u from User u where (:roleId is null or u.role.id = :roleId) and (:loginStart is null  or u.login like %:loginStart%)")
    List<User> getAllUsersByRoleAndLoginStart(@Param("roleId") Long role, @Param("loginStart") String LoginStart);

}
