package ru.alxdv.nfoshop.service;

import ru.alxdv.nfoshop.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Long createEmployee(Employee employee);
    Employee getEmployee(Long id);
    Long updateEmployee(Employee employee);
    void deleteEmployeeById(Long id);
}
