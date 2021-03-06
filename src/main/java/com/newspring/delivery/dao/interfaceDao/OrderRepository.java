package com.newspring.delivery.dao.interfaceDao;

import com.newspring.delivery.entities.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query(
            value = "select o from Order o where" +
                    "((:name  is null or o.name like %:name%)" +
                    "and (:description  is null or o.description like %:description%))" +
                    "and (:address  is null  or o.address like %:address%)" +
                    "and ((:minPrice  is null or o.price > :minPrice)" +
                    "and (:maxPrice  is null  or o.price < :maxPrice))"
    )
    List<Order> advancedOrderSearch(@Param("name") String name,
                                    @Param("description") String description,
                                    @Param("address") String address,
                                    @Param("minPrice") Double minPrice,
                                    @Param("maxPrice") Double maxPrice);

    @Query(
            value = "select o from Order o where o.orderStatus.id = 1"
    )
    List<Order> getAllOrdersWhereStatusIdOne();

}
