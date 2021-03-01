package ru.alxdv.nfoshop.mapper;

import org.mapstruct.Mapper;
import ru.alxdv.nfoshop.dto.OrderDTO;
import ru.alxdv.nfoshop.entity.Order;

@Mapper(componentModel = "spring", uses = {
        CustomerMapper.class,
        EmployeeMapper.class,
        ProductMapper.class})
public interface OrderMapper {
    OrderDTO toDTO(Order order);
    Order toEntity(OrderDTO orderDTO);

}
