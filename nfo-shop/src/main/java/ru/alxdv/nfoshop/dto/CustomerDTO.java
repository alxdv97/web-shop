package ru.alxdv.nfoshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Customer's entity")
public class CustomerDTO {

    @Schema(description = "Customer's email", example = "customer1@email.com")
    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "Email cannot be empty")
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$",
            message = "Incorrect email format")
    private String email;

    @Schema(description = "Customer's first name", example = "C1FirstName")
    @NotBlank(message = "First name cannot be empty")
    @Pattern(regexp = "[^0-9]*",
            message = "First name must not contain numbers")
    @Size(min = 1, max = 50, message = "First name should be less than 50 characters")
    private String firstName;

    @Schema(description = "Customer's last name", example = "C1LastName")
    @NotBlank(message = "Last name cannot be empty")
    @Pattern(regexp = "[^0-9]*",
            message = "Last name must not contain numbers")
    @Size(min = 1, max = 50, message = "Last name should be less than 50 characters")
    private String lastName;

    @Schema(description = "Customer's phone", example = "+11111111111")
    @Pattern(regexp = "\\+\\d{10,14}",
            message = "Phone should match pattern \"+XXXXXXXXXXX\", where X - digit")
    private String phone;

    @Schema(description = "Customer's address", example = "C1Address")
    @Size(max = 500,
            message = "Address should be less than 500 characters")
    private String address;

    @Schema(description = "Customer's orders")
    private Set<OrderDTO> orders;
}
