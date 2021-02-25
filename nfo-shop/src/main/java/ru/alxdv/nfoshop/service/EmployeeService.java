package ru.alxdv.nfoshop.service;

import ru.alxdv.nfoshop.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee createEmployee(Employee employee);
    Employee getEmployee(Long id);
    Employee updateEmployee(Employee employee);
    void deleteEmployeeById(Long id);
}
