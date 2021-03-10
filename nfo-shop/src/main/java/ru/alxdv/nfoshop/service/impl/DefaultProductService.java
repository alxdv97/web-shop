package ru.alxdv.nfoshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alxdv.nfoshop.dto.ProductDTO;
import ru.alxdv.nfoshop.entity.Product;
import ru.alxdv.nfoshop.mapper.ProductMapper;
import ru.alxdv.nfoshop.repository.ProductRepository;
import ru.alxdv.nfoshop.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultProductService implements ProductService {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private ProductMapper mapper;

    @Override
    public List<ProductDTO> getAllProducts() {
        return repo.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long createProduct(ProductDTO product) {
        return repo.save(mapper.toEntity(product)).getId();
    }

    @Override
    public ProductDTO getProduct(Long id) {
        return mapper.toDTO(repo.getOne(id));
    }

    @Override
    public Long updateProduct(ProductDTO product, Long id) {
        Product productFromDb = repo.getOne(id);
        mapper.updateFromDtoToEntity(product, productFromDb);
        return repo.save(productFromDb).getId();
    }

    @Override
    public void deleteProductById(Long id) {
        repo.deleteById(id);
    }
}
