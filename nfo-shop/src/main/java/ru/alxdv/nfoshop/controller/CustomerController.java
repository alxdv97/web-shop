package ru.alxdv.nfoshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alxdv.nfoshop.dto.CustomerDTO;
import ru.alxdv.nfoshop.mapper.CustomerMapper;
import ru.alxdv.nfoshop.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/customers")
@Tag(name = "Customer controller", description = "Provides customer API")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper mapper;

    @GetMapping
    @Operation(
            summary = "Get all customers",
            description = "Returns all customers"
    )
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList()), HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get customer",
            description = "Return customer by ID"
    )
    public ResponseEntity<CustomerDTO> getCustomer(@Parameter(description = "Customer's ID")
                                   @PathVariable Long id) {
        return new ResponseEntity<>(mapper.toDTO(customerService.getCustomer(id)), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Create customer",
            description = "Create and return customer"
    )
    public ResponseEntity<CustomerDTO> createCustomer(@Parameter(description = "Customer")
                                      @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(mapper.toDTO(customerService.createCustomer(mapper.toEntity(customerDTO))),
                HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(
            summary = "Update customer",
            description = "Update and return customer"
    )
    public ResponseEntity<CustomerDTO> updateCustomer(@Parameter(description = "Customer")
                                      @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(mapper.toDTO(customerService.updateCustomer(mapper.toEntity(customerDTO))),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete customer",
            description = "Delete customer by ID"
    )
    public ResponseEntity deleteCustomer(@Parameter(description = "Customer's ID")
                               @PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
