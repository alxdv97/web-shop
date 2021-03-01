package ru.alxdv.nfoshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.alxdv.nfoshop.dto.CustomerDTO;
import ru.alxdv.nfoshop.entity.Customer;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface CustomerMapper {
    @Mapping(target = "orders", ignore = true)
    CustomerDTO toDTO(Customer customer);
    @Mapping(target = "orders", ignore = true)
    Customer toEntity(CustomerDTO customerDTO);
}
