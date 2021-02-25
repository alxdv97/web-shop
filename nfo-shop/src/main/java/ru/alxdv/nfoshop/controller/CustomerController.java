package ru.alxdv.nfoshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alxdv.nfoshop.dto.CustomerDTO;
import ru.alxdv.nfoshop.mapper.CustomerMapper;
import ru.alxdv.nfoshop.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "customer")
@Tag(name="Customer controller", description="Provides customer API")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

    @GetMapping(value = "get-all")
    @Operation(
            summary = "Get all customers",
            description = "Returns all customers"
    )
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomers().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
