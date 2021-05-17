package com.newspring.delivery.entities.user;

import com.newspring.delivery.entities.order.Order;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "phone")
    private String phone;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Roles roles;


    @OneToMany(fetch = FetchType.LAZY)
    private List<Order> orders;


}
