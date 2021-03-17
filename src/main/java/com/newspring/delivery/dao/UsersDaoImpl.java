package com.newspring.delivery.dao;


import com.newspring.delivery.entities.ChangeRoleOnUser;
import com.newspring.delivery.entities.Role;
import com.newspring.delivery.entities.User;
import com.newspring.delivery.entities.UserWithRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
@Slf4j
public class UsersDaoImpl implements UsersDao {

    public static final String INSERT_USER = "INSERT INTO users(login,password,role_id, phone) values (:login,:password,:roleId, :phone)";
    public static final String SELECT_ALL_FROM_ROLES = "SELECT id, name FROM roles";
    public static final String GET_ALL_USERS_WITH_ROLES = "select" +
            "       u.id id, \n" +
            "       u.login login,\n" +
            "       r.id role_id,\n " +
            "       r.name role_name\n" +
            "from users u\n" +
            "         left join roles r on u.role_id = r.id\n" +
            "where (u.login like :loginStart or cast(:loginStart as varchar(255)) is null)\n" +
            "  and (r.id = :roleId or cast (:roleId as bigint) is null)";
    public static final String UPDATE_ROLE = "update users as u set role_id = :roleId where u.id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void roleChange(ChangeRoleOnUser role) {
        try {
            jdbcTemplate.update(UPDATE_ROLE,
                    new BeanPropertySqlParameterSource(role));
            log.info("update in UserDaoImpl implemented");
        }catch (Exception e){
            log.error(" error in update role in  roleChange / UsersDaoImpl");
        }
    }


    @Override
    public void add(User user) {
        try {
            jdbcTemplate.update(
                    INSERT_USER,
                    new BeanPropertySqlParameterSource(user));
            log.info("ALL GOOD in UsersDaoImpl");
        } catch (Exception e) {
            log.error("ERROR ADD USER in UsersDaoImpl {}", e.getMessage());
        }

    }

    @Override
    public List<Role> getAllRoles() {
        return jdbcTemplate.query(SELECT_ALL_FROM_ROLES,
                new BeanPropertyRowMapper<>(Role.class));
    }

    @Override
    public List<UserWithRole> getAllUsersByRoleAndLoginStart(Long role, String loginStart) {
        return jdbcTemplate.query(GET_ALL_USERS_WITH_ROLES,
                new MapSqlParameterSource("loginStart", loginStart == null ? null : loginStart + "%")
                        .addValue("roleId", role)
                ,
                new BeanPropertyRowMapper<>(UserWithRole.class));
    }
}










