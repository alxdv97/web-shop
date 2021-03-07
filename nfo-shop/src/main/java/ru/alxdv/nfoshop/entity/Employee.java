package ru.alxdv.nfoshop.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "Email cannot be empty")
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$",
            message = "Incorrect email format")
    private String email;

    @Column(name = "first_name")
    @NotBlank(message = "First name cannot be empty")
    @Pattern(regexp = "[^0-9]*",
            message = "First name must not contain numbers")
    @Size(min = 1, max = 50, message = "First name should be less than 50 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name cannot be empty")
    @Pattern(regexp = "[^0-9]*",
            message = "Last name must not contain numbers")
    @Size(min = 1, max = 50, message = "Last name should be less than 50 characters")
    private String lastName;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "employee",
            cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = false,
            fetch = FetchType.EAGER)
    private Set<Order> orders;

    @Column(name = "phone")
    @Pattern(regexp = "\\+\\d{10,14}",
            message = "Phone should match pattern \"+XXXXXXXXXXX\", where X - digit")
    private String phone;

    @Column(name = "position")
    @Size(max = 30,
            message = "Position should be less than 30 characters")
    private String position;
}
