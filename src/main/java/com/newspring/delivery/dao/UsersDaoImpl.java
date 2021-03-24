package com.newspring.delivery.dao;


import com.newspring.delivery.dto.options_with_user.AdvancedOrderDto;
import com.newspring.delivery.dto.options_with_user.AdvanceOrderFiltersDto;
import com.newspring.delivery.entities.*;
import com.newspring.delivery.mappers.UserMapper;
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

    private UserMapper userMapper;

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

    public static final String CREATE_ORDER = "insert into orders(author_user_id,  price, name, description, address, status_id)" +
            " VALUES (:authorUserId,  :price, :name, :description, :address, :statusId)";

    public static final String CHANGE_ORDER = "update orders\n" +
            "set    name=:name,\n" +
            "    description=:description,\n" +
            "    address =:address\n" +
            "where id = cast(:orderId as bigint) and status_id = 1";

    public static final String DELETE_FROM_ORDERS_WHERE_ID_ORDER_ID = "DELETE FROM orders WHERE id = :orderId";

    public static final String ADVANCE_ORDER = "select id as \"orderId\", name,description,address,price from orders " +
            "where  ((name like :name or cast(:name as varchar(255)) is null)" +
            "and (description like :description or cast(:description as varchar(255)) is null))" +
            "and (address like :address or cast(:address as varchar(255)) is null)" +
            "and ((price > :minPrice or cast(:minPrice as numeric) is null)" +
            "and (price < :maxPrice or cast(:maxPrice as numeric) is null))";

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
            int a = jdbcTemplate.update(
                    CHANGE_ORDER,
                    new BeanPropertySqlParameterSource(changeOrder));
            if (a == 0) {
                log.info("входные данные : {}",changeOrder);
                log.info("Order cannot be changed");
            } else {
                log.info("Change order successfully implemented");
            }
        } catch (Exception e) {
            log.error("Error in changeOrder / UsersDaoImpl {}", e.getMessage(), e);
        }
    }


    @Override
    public void removeOrder(DeleteOrderRequest deleteOrder) {
        try {
            int a = jdbcTemplate.update(DELETE_FROM_ORDERS_WHERE_ID_ORDER_ID,
                    new BeanPropertySqlParameterSource(deleteOrder));
            log.info("Delete in UserDaoImpl implemented");
        } catch (Exception e) {
            log.info("Error in Delete method / UserDaoImpl : {} ", e.getMessage(), e);
        }


    }

    @Override
    public void roleChange(ChangeRoleOnUser role) {
        try {
            jdbcTemplate.update(UPDATE_ROLE,
                    new BeanPropertySqlParameterSource(role));
            log.info("update in UserDaoImpl implemented");
        } catch (Exception e) {
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


    @Override
    public List<AdvanceOrder> advancedOrderSearch(AdvanceOrdersFilters advancedOrder) {
        log.info(" входные данные : {}", advancedOrder);
        return  jdbcTemplate.query(ADVANCE_ORDER,
                new MapSqlParameterSource("name", advancedOrder.getName() == null ? null : "%" + advancedOrder.getName() + "%")
                        .addValue("description", advancedOrder.getDescription() == null ? null : "%" + advancedOrder.getDescription() + "%")
                        .addValue("address", advancedOrder.getAddress() == null ? null : "%" + advancedOrder.getAddress() + "%")
                        .addValue("minPrice", advancedOrder.getMinPrice())
                        .addValue("maxPrice", advancedOrder.getMaxPrice())
                ,
                new BeanPropertyRowMapper<>(AdvanceOrder.class)
        );
    }
}










