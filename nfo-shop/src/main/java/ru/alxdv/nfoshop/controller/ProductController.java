package ru.alxdv.nfoshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alxdv.nfoshop.dto.ProductDTO;
import ru.alxdv.nfoshop.mapper.ProductMapper;
import ru.alxdv.nfoshop.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/products")
@Tag(name = "Product controller", description = "Provides product API")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper mapper;

    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Returns all products"
    )
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "{id}")
    @Operation(
            summary = "Get product",
            description = "Return product by ID"
    )
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProduct(@Parameter(description = "Product's ID")
                                   @PathVariable Long id) {
        return mapper.toDTO(productService.getProduct(id));
    }

    @PostMapping
    @Operation(
            summary = "Create product",
            description = "Create and return product"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@Parameter(description = "Product")
                                      @RequestBody ProductDTO productDTO) {
        return mapper.toDTO(productService.createProduct(mapper.toEntity(productDTO)));
    }

    @PutMapping
    @Operation(
            summary = "Update product",
            description = "Update and return product"
    )
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProduct(@Parameter(description = "Product")
                                      @RequestBody ProductDTO productDTO) {
        return mapper.toDTO(productService.updateProduct(mapper.toEntity(productDTO)));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Delete product",
            description = "Delete product by ID"
    )
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@Parameter(description = "Product's ID")
                               @PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
