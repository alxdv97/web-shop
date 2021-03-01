package ru.alxdv.nfoshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alxdv.nfoshop.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
