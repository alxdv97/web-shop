package ru.alxdv.nfoshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.alxdv.nfoshop.dto.OrderDTO;
import ru.alxdv.nfoshop.entity.Order;
import ru.alxdv.nfoshop.service.CustomerService;
import ru.alxdv.nfoshop.service.EmployeeService;

@Mapper(componentModel = "spring", uses = {
        CustomerMapper.class,
        EmployeeMapper.class,
        ProductMapper.class,
        EmployeeService.class,
        CustomerService.class})
public interface OrderMapper {
    @Mapping(source = "order.employee.id", target = "employeeId")
    @Mapping(source = "order.customer.id", target = "customerId")
    OrderDTO toDTO(Order order);
    @Mapping(source = "orderDTO.employeeId", target = "employee")
    @Mapping(source = "orderDTO.customerId", target = "customer")
    Order toEntity(OrderDTO orderDTO);

}
