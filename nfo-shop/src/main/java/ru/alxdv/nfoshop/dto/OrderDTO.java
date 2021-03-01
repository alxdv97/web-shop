package ru.alxdv.nfoshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Setter
@Getter
@Schema(description = "Order's entity")
public class OrderDTO {

    @Schema(description = "Order's ID", example = "1")
    private Long id;

    @Schema(description = "Order's creation date", example = "2021-02-24 19:10:25.000000")
    private Timestamp creationDate;

    @Schema(description = "Order's delivery date", example = "2021-02-24 20:10:25.000000")
    private Timestamp deliveryDate;

    @Schema(description = "Order's customer")
    private CustomerDTO customer;

    @Schema(description = "Order's employee")
    private EmployeeDTO employee;

    @Schema(description = "Order's products")
    private Set<ProductDTO> products;
}
