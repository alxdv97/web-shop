package ru.alxdv.nfoshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Order's entity")
public class OrderDTO {

    @Schema(description = "Order's creation date", example = "2021-02-24 19:10:25.000000")
    private Timestamp creationDate;

    @Schema(description = "Order's delivery date", example = "2021-02-24 20:10:25.000000")
    private Timestamp deliveryDate;

    @Schema(description = "Order's customer ID", example = "1")
    private Long customerId;

    @Schema(description = "Order's employee ID", example = "1")
    private Long employeeId;

    @Valid
    @Schema(description = "Order's products")
    private Set<ProductDTO> products;
}
