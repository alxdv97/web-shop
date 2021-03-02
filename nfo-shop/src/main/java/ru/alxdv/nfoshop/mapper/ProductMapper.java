package ru.alxdv.nfoshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.alxdv.nfoshop.dto.ProductDTO;
import ru.alxdv.nfoshop.entity.Product;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface ProductMapper {
    @Mapping(target = "orders", ignore = true)
    ProductDTO toDTO(Product product);
    @Mapping(target = "orders", ignore = true)
    Product toEntity(ProductDTO productDTO);
}
