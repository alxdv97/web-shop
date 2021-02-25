package ru.alxdv.nfoshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alxdv.nfoshop.entity.Product;
import ru.alxdv.nfoshop.repository.ProductRepository;
import ru.alxdv.nfoshop.service.ProductService;

import java.util.List;

@Service
public class DefaultProductService implements ProductService {

    @Autowired
    private ProductRepository repo;

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return repo.save(product);
    }

    @Override
    public Product getProduct(Long id) {
        return repo.getOne(id);
    }

    @Override
    public Product updateProduct(Product product) {
        return repo.save(product);
    }

    @Override
    public void deleteProductById(Long id) {
        repo.deleteById(id);
    }
}
