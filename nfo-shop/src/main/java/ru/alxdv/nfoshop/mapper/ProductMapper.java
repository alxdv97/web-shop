package ru.alxdv.nfoshop.mapper;

import org.mapstruct.*;
import ru.alxdv.nfoshop.dto.ProductDTO;
import ru.alxdv.nfoshop.entity.Product;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface ProductMapper {
    @Mapping(target = "orders", ignore = true)
    ProductDTO toDTO(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    Product toEntity(ProductDTO productDTO);

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    void updateFromDtoToEntity(ProductDTO dto, @MappingTarget Product entity);
}
