package ru.alxdv.nfoshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Schema(description = "Customer's entity")
public class CustomerDTO {

    @Schema(description = "Customer's ID", example = "1")
    private Long id;

    @Schema(description = "Customer's email", example = "customer1@email.com")
    private String email;

    @Schema(description = "Customer's first name", example = "C1FirstName")
    private String firstName;

    @Schema(description = "Customer's last name", example = "C1LastName")
    private String lastName;

    @Schema(description = "Customer's phone", example = "+11111111111")
    private String phone;

    @Schema(description = "Customer's address", example = "C1Address")
    private String address;

    @JsonIgnore
    @Schema(description = "Customer's orders")
    private Set<OrderDTO> orders;
}
