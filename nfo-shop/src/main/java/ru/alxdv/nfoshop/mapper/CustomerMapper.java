package ru.alxdv.nfoshop.mapper;

import org.mapstruct.*;
import ru.alxdv.nfoshop.dto.CustomerDTO;
import ru.alxdv.nfoshop.entity.Customer;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface CustomerMapper {

    CustomerDTO toDTO(Customer customer);

    @Mapping(target = "id", ignore = true)
    Customer toEntity(CustomerDTO customerDTO);

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    void updateFromDtoToEntity(CustomerDTO dto, @MappingTarget Customer entity);
}
