package ru.alxdv.nfoshop.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "employees")
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "employee",
            cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = false,
            fetch = FetchType.EAGER)
    private Set<Order> orders;

    @Column(name = "phone")
    private String phone;

    @Column(name = "position")
    private String position;
}
