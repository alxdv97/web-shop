package ru.alxdv.nfoshop.mapper;

import org.mapstruct.Mapper;
import ru.alxdv.nfoshop.dto.ProductDTO;
import ru.alxdv.nfoshop.entity.Product;

@Mapper
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO productDTO);
}
