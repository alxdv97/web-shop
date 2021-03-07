package ru.alxdv.nfoshop.service;

import ru.alxdv.nfoshop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    Long createProduct(ProductDTO product);
    ProductDTO getProduct(Long id);
    Long updateProduct(ProductDTO product, Long id);
    void deleteProductById(Long id);
}
