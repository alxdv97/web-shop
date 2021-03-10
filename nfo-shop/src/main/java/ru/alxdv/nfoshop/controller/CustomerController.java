package ru.alxdv.nfoshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alxdv.nfoshop.dto.CustomerDTO;
import ru.alxdv.nfoshop.service.CustomerService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(value = "/api/customers")
@Tag(name = "Customer controller", description = "Provides customer API")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    @Operation(
            summary = "Get all customers",
            description = "Returns all customers"
    )
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get customer",
            description = "Return customer by ID"
    )
    public ResponseEntity<CustomerDTO> getCustomer(@Parameter(description = "Customer's ID")
                                                   @PathVariable @Positive Long id) {
        return new ResponseEntity<>(customerService.getCustomer(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Create customer",
            description = "Create customer and return his ID"
    )
    public ResponseEntity<Long> createCustomer(@Parameter(description = "Customer")
                                               @RequestBody @Valid CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.createCustomer(customerDTO),
                HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(
            summary = "Update customer",
            description = "Update customer and return his ID"
    )
    public ResponseEntity<Long> updateCustomer(@Parameter(description = "Customer data")
                                               @RequestBody @Valid CustomerDTO customerDTO,
                                               @Parameter(description = "Customer ID")
                                               @RequestParam @Positive Long customerId) {
        return new ResponseEntity<>(customerService.updateCustomer(customerDTO, customerId),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete customer",
            description = "Delete customer by ID"
    )
    public ResponseEntity deleteCustomer(@Parameter(description = "Customer's ID")
                                         @PathVariable @Positive Long id) {
        customerService.deleteCustomerById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
