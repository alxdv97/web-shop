package ru.alxdv.nfoshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alxdv.nfoshop.dto.CustomerDTO;
import ru.alxdv.nfoshop.entity.Customer;
import ru.alxdv.nfoshop.mapper.CustomerMapper;
import ru.alxdv.nfoshop.repository.CustomerRepository;
import ru.alxdv.nfoshop.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultCustomerService implements CustomerService {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private CustomerMapper mapper;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return repo.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long createCustomer(CustomerDTO customer) {
        return repo.save(mapper.toEntity(customer)).getId();
    }

    @Override
    public CustomerDTO getCustomer(Long id) {
        return mapper.toDTO(repo.getOne(id));
    }

    @Override
    public Long updateCustomer(CustomerDTO customer, Long id) {
        Customer customerFromDb = repo.getOne(id);
        mapper.updateFromDtoToEntity(customer, customerFromDb);
        return repo.save(customerFromDb).getId();
    }

    @Override
    public void deleteCustomerById(Long id) {
        repo.deleteById(id);
    }
}
