package ru.alxdv.nfoshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "creation_date", nullable = false)
    private Timestamp creationDate;

    @Column(name = "delivery_date")
    private Timestamp deliveryDate;

}
