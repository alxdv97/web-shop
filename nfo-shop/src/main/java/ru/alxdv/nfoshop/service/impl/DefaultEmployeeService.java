package ru.alxdv.nfoshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alxdv.nfoshop.dto.EmployeeDTO;
import ru.alxdv.nfoshop.entity.Employee;
import ru.alxdv.nfoshop.mapper.EmployeeMapper;
import ru.alxdv.nfoshop.repository.EmployeeRepository;
import ru.alxdv.nfoshop.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultEmployeeService implements EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    @Autowired
    private EmployeeMapper mapper;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return repo.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long createEmployee(EmployeeDTO employee) {
        return repo.save(mapper.toEntity(employee)).getId();
    }

    @Override
    public EmployeeDTO getEmployee(Long id) {
        return mapper.toDTO(repo.getOne(id));
    }

    @Override
    public Long updateEmployee(EmployeeDTO employee, Long id) {
        Employee employeeFromDb = repo.getOne(id);
        mapper.updateFromDtoToEntity(employee, employeeFromDb);
        return repo.save(employeeFromDb).getId();
    }

    @Override
    public void deleteEmployeeById(Long id) {
        repo.deleteById(id);
    }
}
