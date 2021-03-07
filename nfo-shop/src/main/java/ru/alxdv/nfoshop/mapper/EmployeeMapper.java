package ru.alxdv.nfoshop.mapper;

import org.mapstruct.Mapper;
import ru.alxdv.nfoshop.dto.EmployeeDTO;
import ru.alxdv.nfoshop.entity.Employee;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface EmployeeMapper {
    EmployeeDTO toDTO(Employee employee);
    Employee toEntity(EmployeeDTO employeeDTO);
}
