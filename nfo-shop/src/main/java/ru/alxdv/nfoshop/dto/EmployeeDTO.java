package ru.alxdv.nfoshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Schema(description = "Employee's entity")
public class EmployeeDTO {

    @Schema(description = "Employee's ID", example = "1")
    private Long id;

    @Schema(description = "Employee's email", example = "employee1@email.com")
    private String email;

    @Schema(description = "Employee's first name", example = "E1FirstName")
    private String firstName;

    @Schema(description = "Employee's last name", example = "E1LastName")
    private String lastName;

    @Schema(description = "Employee's phone", example = "+10000000000")
    private String phone;

    @Schema(description = "Employee's position", example = "E1Position")
    private String position;

    @Schema(description = "Employee's orders")
    private Set<OrderDTO> orders;
}
