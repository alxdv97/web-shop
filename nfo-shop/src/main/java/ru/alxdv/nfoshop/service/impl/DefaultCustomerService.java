package ru.alxdv.nfoshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alxdv.nfoshop.entity.Customer;
import ru.alxdv.nfoshop.repository.CustomerRepository;
import ru.alxdv.nfoshop.service.CustomerService;

import java.util.List;

@Service
public class DefaultCustomerService implements CustomerService {

    @Autowired
    private CustomerRepository repo;

    @Override
    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }
}
