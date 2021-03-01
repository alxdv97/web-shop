package ru.alxdv.nfoshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alxdv.nfoshop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
