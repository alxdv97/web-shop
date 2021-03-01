package ru.alxdv.nfoshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Schema(description = "Product's entity")
public class ProductDTO {

    @Schema(description = "Product's ID", example = "1")
    private Long id;

    @Schema(description = "Product's name", example = "product1")
    private String name;

    @Schema(description = "Product's price", example = "100")
    private Double price;

    @Schema(description = "Product's description", example = "Product1Description")
    private String description;

    @JsonIgnore
    @Schema(description = "Product's orders")
    private Set<OrderDTO> orders;
}
