package ru.alxdv.nfoshop.mapper;

import org.mapstruct.*;
import ru.alxdv.nfoshop.dto.OrderDTO;
import ru.alxdv.nfoshop.entity.Order;
import ru.alxdv.nfoshop.service.CustomerService;
import ru.alxdv.nfoshop.service.EmployeeService;

@Mapper(componentModel = "spring", uses = {
        CustomerMapper.class,
        EmployeeMapper.class,
        ProductMapper.class,
        EmployeeService.class,
        CustomerService.class},
        builder = @Builder(disableBuilder = true))
public interface OrderMapper {

    @Mapping(source = "order.employee.id", target = "employeeId")
    @Mapping(source = "order.customer.id", target = "customerId")
    OrderDTO toDTO(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "orderDTO.employeeId", target = "employee")
    @Mapping(source = "orderDTO.customerId", target = "customer")
    Order toEntity(OrderDTO orderDTO);

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    void updateFromDtoToEntity(OrderDTO dto, @MappingTarget Order entity);
}
