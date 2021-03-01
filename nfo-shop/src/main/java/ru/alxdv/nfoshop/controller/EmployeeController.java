package ru.alxdv.nfoshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alxdv.nfoshop.dto.EmployeeDTO;
import ru.alxdv.nfoshop.mapper.EmployeeMapper;
import ru.alxdv.nfoshop.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/employees")
@Tag(name = "Employee controller", description = "Provides employee API")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private final EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);

    @GetMapping
    @Operation(
            summary = "Get all employees",
            description = "Returns all employees"
    )
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "{id}")
    @Operation(
            summary = "Get employee",
            description = "Return employee by ID"
    )
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO getEmployee(@Parameter(description = "Employee's ID")
                                   @PathVariable Long id) {
        return mapper.toDTO(employeeService.getEmployee(id));
    }

    @PostMapping
    @Operation(
            summary = "Create employee",
            description = "Create and return employee"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDTO createEmployee(@Parameter(description = "Employee")
                                      @RequestBody EmployeeDTO employeeDTO) {
        return mapper.toDTO(employeeService.createEmployee(mapper.toEntity(employeeDTO)));
    }

    @PutMapping
    @Operation(
            summary = "Update employee",
            description = "Update and return employee"
    )
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO updateEmployee(@Parameter(description = "Employee")
                                      @RequestBody EmployeeDTO employeeDTO) {
        return mapper.toDTO(employeeService.updateEmployee(mapper.toEntity(employeeDTO)));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Delete employee",
            description = "Delete employee by ID"
    )
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@Parameter(description = "Employee's ID")
                               @PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
    }
}
