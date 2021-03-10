package ru.alxdv.nfoshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Product's entity")
public class ProductDTO {

    @Schema(description = "Product's name", example = "product1")
    @NotBlank(message = "Product's name cannot be empty")
    @Size(min = 1, max = 100, message = "Product name should be less than 100 characters")
    private String name;

    @Schema(description = "Product's price", example = "100")
    @NotNull
    @Positive
    private Double price;

    @Schema(description = "Product's description", example = "Product1Description")
    private String description;

    @JsonIgnore
    @Schema(description = "Product's orders")
    private Set<OrderDTO> orders;
}
