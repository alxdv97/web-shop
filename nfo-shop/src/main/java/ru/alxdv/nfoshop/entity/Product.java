package ru.alxdv.nfoshop.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "Product's name cannot be empty")
    @Size(min = 1, max = 100,
            message = "Product name should be less than 100 characters")
    private String name;

    @Column(name = "price")
    @NotNull
    @Positive
    private Double price;

    @Column(name = "description")
    private String description;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ManyToMany(mappedBy = "products")
    private Set<Order> orders = new HashSet<>();
}

