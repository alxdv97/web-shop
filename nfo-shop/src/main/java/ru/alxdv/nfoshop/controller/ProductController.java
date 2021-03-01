package ru.alxdv.nfoshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alxdv.nfoshop.dto.ProductDTO;
import ru.alxdv.nfoshop.mapper.ProductMapper;
import ru.alxdv.nfoshop.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/products")
@Tag(name = "Product controller", description = "Provides product API")
public class ProductController {

    @Autowired
    private ProductService productService;

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Returns all products"
    )
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get product",
            description = "Return product by ID"
    )
    public ResponseEntity<ProductDTO> getProduct(@Parameter(description = "Product's ID")
                                   @PathVariable Long id) {
        return new ResponseEntity<>(mapper.toDTO(productService.getProduct(id)),HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Create product",
            description = "Create and return product"
    )
    public ResponseEntity<ProductDTO> createProduct(@Parameter(description = "Product")
                                      @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(mapper.toDTO(productService.createProduct(mapper.toEntity(productDTO))),
                HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(
            summary = "Update product",
            description = "Update and return product"
    )
    public ResponseEntity<ProductDTO> updateProduct(@Parameter(description = "Product")
                                      @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(mapper.toDTO(productService.updateProduct(mapper.toEntity(productDTO))),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete product",
            description = "Delete product by ID"
    )
    public ResponseEntity deleteProduct(@Parameter(description = "Product's ID")
                               @PathVariable Long id) {
        productService.deleteProductById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}