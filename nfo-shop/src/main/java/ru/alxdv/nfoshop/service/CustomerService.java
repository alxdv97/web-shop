package ru.alxdv.nfoshop.service;

import ru.alxdv.nfoshop.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Long createCustomer(Customer customer);
    Customer getCustomer(Long id);
    Long updateCustomer(Customer customer);
    void deleteCustomerById(Long id);
}
