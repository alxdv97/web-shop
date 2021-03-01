package ru.alxdv.nfoshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alxdv.nfoshop.dto.CustomerDTO;
import ru.alxdv.nfoshop.mapper.CustomerMapper;
import ru.alxdv.nfoshop.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/customers")
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
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "{id}")
    @Operation(
            summary = "Get customer",
            description = "Return customer by ID"
    )
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomer(@Parameter(description = "Customer's ID")
                                   @PathVariable Long id) {
        return mapper.toDTO(customerService.getCustomer(id));
    }

    @PostMapping
    @Operation(
            summary = "Create customer",
            description = "Create and return customer"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@Parameter(description = "Customer")
                                      @RequestBody CustomerDTO customerDTO) {
        return mapper.toDTO(customerService.createCustomer(mapper.toEntity(customerDTO)));
    }

    @PutMapping
    @Operation(
            summary = "Update customer",
            description = "Update and return customer"
    )
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@Parameter(description = "Customer")
                                      @RequestBody CustomerDTO customerDTO) {
        return mapper.toDTO(customerService.updateCustomer(mapper.toEntity(customerDTO)));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Delete customer",
            description = "Delete customer by ID"
    )
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@Parameter(description = "Customer's ID")
                               @PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }

}
