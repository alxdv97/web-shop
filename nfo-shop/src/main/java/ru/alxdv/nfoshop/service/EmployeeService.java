package ru.alxdv.nfoshop.service;

import ru.alxdv.nfoshop.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    Long createEmployee(EmployeeDTO employee);
    EmployeeDTO getEmployee(Long id);
    Long updateEmployee(EmployeeDTO employee, Long id);
    void deleteEmployeeById(Long id);
}
