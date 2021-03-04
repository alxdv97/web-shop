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
    public Long createProduct(Product product) {
        return repo.save(product).getId();
    }

    @Override
    public Product getProduct(Long id) {
        return repo.getOne(id);
    }

    @Override
    public Long updateProduct(Product product) {
        return repo.save(product).getId();
    }

    @Override
    public void deleteProductById(Long id) {
        repo.deleteById(id);
    }
}
