package ru.alxdv.nfoshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alxdv.nfoshop.dto.ProductDTO;
import ru.alxdv.nfoshop.mapper.ProductMapper;
import ru.alxdv.nfoshop.service.ProductService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/products")
@Tag(name = "Product controller", description = "Provides product API")
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Returns all products"
    )
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get product",
            description = "Return product by ID"
    )
    public ResponseEntity<ProductDTO> getProduct(@Parameter(description = "Product's ID")
                                                 @PathVariable @Positive Long id) {
        return new ResponseEntity<>(productService.getProduct(id),HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Create product",
            description = "Create product and return its ID"
    )
    public ResponseEntity<Long> createProduct(@Parameter(description = "Product")
                                              @RequestBody @Valid ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(productDTO),
                HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(
            summary = "Update product",
            description = "Update product and return its ID"
    )
    public ResponseEntity<Long> updateProduct(@Parameter(description = "Product data")
                                              @RequestBody @Valid ProductDTO productDTO,
                                              @Parameter(description = "Product ID")
                                              @RequestParam @Positive Long productId) {
        return new ResponseEntity<>(productService.updateProduct(productDTO, productId),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete product",
            description = "Delete product by ID"
    )
    public ResponseEntity deleteProduct(@Parameter(description = "Product's ID")
                                        @PathVariable @Positive Long id) {
        productService.deleteProductById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}