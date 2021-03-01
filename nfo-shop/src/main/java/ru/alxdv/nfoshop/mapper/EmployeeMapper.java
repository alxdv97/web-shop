package ru.alxdv.nfoshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.alxdv.nfoshop.dto.EmployeeDTO;
import ru.alxdv.nfoshop.entity.Employee;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface EmployeeMapper {
    @Mapping(target = "orders", ignore = true)
    EmployeeDTO toDTO(Employee employee);
    @Mapping(target = "orders", ignore = true)
    Employee toEntity(EmployeeDTO employeeDTO);
}
