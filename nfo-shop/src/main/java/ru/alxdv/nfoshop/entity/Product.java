package ru.alxdv.nfoshop.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ManyToMany(mappedBy = "products")
    private Set<Order> orders = new HashSet<>();
}

