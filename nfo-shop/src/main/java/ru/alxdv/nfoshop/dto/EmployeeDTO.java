package ru.alxdv.nfoshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Employee's entity")
public class EmployeeDTO {

    @Schema(description = "Employee's email", example = "employee1@email.com")
    @NotBlank(message = "Email cannot be empty")
    @Email
    private String email;

    @Schema(description = "Employee's first name", example = "E1FirstName")
    @NotBlank(message = "First name cannot be empty")
    @Pattern(regexp = "[^0-9]*",
            message = "First name must not contain numbers")
    @Size(min = 1, max = 50, message = "First name should be less than 50 characters")
    private String firstName;

    @Schema(description = "Employee's last name", example = "E1LastName")
    @NotBlank(message = "Last name cannot be empty")
    @Pattern(regexp = "[^0-9]*",
            message = "Last name must not contain numbers")
    @Size(min = 1, max = 50, message = "Last name should be less than 50 characters")
    private String lastName;

    @Schema(description = "Employee's phone", example = "+10000000000")
    @Pattern(regexp = "\\+\\d{10,14}",
            message = "Phone should match pattern \"+XXXXXXXXXXX\"")
    private String phone;

    @Schema(description = "Employee's position", example = "E1Position")
    @Size(max = 30,
            message = "Position should be less than 30 characters")
    private String position;

    @Valid
    @Schema(description = "Employee's orders")
    private Set<OrderDTO> orders;
}
