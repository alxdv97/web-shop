package ru.alxdv.nfoshop.mapper;

import org.mapstruct.*;
import ru.alxdv.nfoshop.dto.EmployeeDTO;
import ru.alxdv.nfoshop.entity.Employee;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface EmployeeMapper {

    EmployeeDTO toDTO(Employee employee);
    @Mapping(target = "id", ignore = true)

    Employee toEntity(EmployeeDTO employeeDTO);
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)

    void updateFromDtoToEntity(EmployeeDTO dto, @MappingTarget Employee entity);
}
