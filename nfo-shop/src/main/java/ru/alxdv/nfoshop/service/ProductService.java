package ru.alxdv.nfoshop.service;

import ru.alxdv.nfoshop.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product createProduct(Product product);
    Product getProduct(Long id);
    Product updateProduct(Product product);
    void deleteProductById(Long id);
}
