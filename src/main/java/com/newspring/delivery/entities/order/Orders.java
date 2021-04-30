package com.newspring.delivery.entities.order;

import com.newspring.delivery.entities.user.Users;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "author_user_id")
    private Long authorUserId;
    @Column(name = "executor_user_id")
    private Long executorUserId;
    @Column(name = "price")
    private Double price;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "address")
    private String address;
    @Column(name = "status_id")
    private Long statusId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "author_user_id", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "executor_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    })
    private Users users;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", insertable = false,updatable = false)
    private OrderStatus orderStatus;
}