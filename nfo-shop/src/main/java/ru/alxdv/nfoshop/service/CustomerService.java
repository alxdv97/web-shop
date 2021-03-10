package ru.alxdv.nfoshop.service;

import ru.alxdv.nfoshop.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    Long createCustomer(CustomerDTO customer);
    CustomerDTO getCustomer(Long id);
    Long updateCustomer(CustomerDTO customer, Long id);
    void deleteCustomerById(Long id);
}
