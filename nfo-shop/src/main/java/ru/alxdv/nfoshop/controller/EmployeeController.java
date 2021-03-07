package ru.alxdv.nfoshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alxdv.nfoshop.dto.EmployeeDTO;
import ru.alxdv.nfoshop.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/employees")
@Tag(name = "Employee controller", description = "Provides employee API")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    @Operation(
            summary = "Get all employees",
            description = "Returns all employees"
    )
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get employee",
            description = "Return employee by ID"
    )
    public ResponseEntity<EmployeeDTO> getEmployee(@Parameter(description = "Employee's ID")
                                                   @PathVariable Long id) {
        return new ResponseEntity<>(employeeService.getEmployee(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Create employee",
            description = "Create employee and return his ID"
    )
    public ResponseEntity<Long> createEmployee(@Parameter(description = "Employee")
                                               @RequestBody EmployeeDTO employeeDTO) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeDTO),
                HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(
            summary = "Update employee",
            description = "Update employee and return his ID"
    )
    public ResponseEntity<Long> updateEmployee(@Parameter(description = "Employee data")
                                               @RequestBody EmployeeDTO employeeDTO,
                                               @Parameter(description = "Employee ID")
                                               @RequestParam Long employeeId) {
        return new ResponseEntity<>(employeeService.updateEmployee(employeeDTO, employeeId),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete employee",
            description = "Delete employee by ID"
    )
    public ResponseEntity deleteEmployee(@Parameter(description = "Employee's ID")
                                         @PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
