package com.newspring.delivery.entities.order;

import com.newspring.delivery.entities.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "price")
    private Double price;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "address")
    private String address;
 /*   @Column(name = "status_id")
    private Long statusId;*/

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_user_id", referencedColumnName = "id")
    private User customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "executor_user_id", referencedColumnName = "id")
    private User executor;

//, insertable = false, updatable = false
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private OrderStatus orderStatus;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", customer=" + customer +
                ", executor=" + executor +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
