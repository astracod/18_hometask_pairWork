package com.newspring.delivery.dao.interfaceDao;

import com.newspring.delivery.entities.user.Roles;
import com.newspring.delivery.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UsersRepository extends JpaRepository<User, Long> {


    @Query(value = "select u  from User  u  where u.login=:login ")
    User findByLogin(String login);


    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.roleId = :roleId  WHERE u.id = :userId")
    void roleChange(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * учесть , что JpaRepository<Roles,Long>
     *
     * @return
     */
    @Query(value = "SELECT * FROM roles", nativeQuery = true)
    List<Roles> getAllRoles();

    @Query(value = "select u from User u where u.roleId= :roleId and (:loginStart is null  or u.login like %:loginStart%)")
    List<User> getAllUsersByRoleAndLoginStart(@Param("roleId") Long role, @Param("loginStart") String LoginStart);

}
