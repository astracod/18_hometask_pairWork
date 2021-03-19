package com.newspring.delivery.dao;


import com.newspring.delivery.entities.*;
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

    public static final String CREATE_ORDER = "insert into orders(author_user_id, executor_user_id, price, name, description, address, status_id)" +
            " VALUES (:authorUserId, :executorUserId, :price, :name, :description, :address, :statusId)";
    public static final String CHANGE_ORDER = "update orders\n" +
            "set    name=:name,\n" +
            "    description=:description,\n" +
            "    address =:address\n" +
            "where id = :orderId and status_id = 1";

    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public void createOrder(Order order) {
        try {
            jdbcTemplate.update(
                    CREATE_ORDER,
                    new BeanPropertySqlParameterSource(order));
        } catch (Exception e) {
            log.error("error in createOrder / UsersDaoImpl {}", e.getMessage(), e);
        }
    }



    @Override
    public void changeOrder(ChangeOrder changeOrder) {
        try {
            int a =  jdbcTemplate.update(
                    CHANGE_ORDER,
                    new BeanPropertySqlParameterSource(changeOrder));
            if (a == 0){
                log.info("Order cannot be changed");
            }else {
                log.info("Change order successfully implemented");
            }
        } catch (Exception e) {
            log.error("Error in changeOrder / UsersDaoImpl {}", e.getMessage(), e);
        }
    }

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










