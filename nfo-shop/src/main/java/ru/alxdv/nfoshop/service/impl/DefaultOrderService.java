package ru.alxdv.nfoshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alxdv.nfoshop.entity.Order;
import ru.alxdv.nfoshop.repository.OrderRepository;
import ru.alxdv.nfoshop.service.OrderService;

import java.util.List;

@Service
public class DefaultOrderService implements OrderService {

    @Autowired
    private OrderRepository repo;

    @Override
    public List<Order> getAllOrders() {
        return repo.findAll();
    }

    @Override
    public Order createOrder(Order order) {
        return repo.save(order);
    }

    @Override
    public Order getOrder(Long id) {
        return repo.getOne(id);
    }

    @Override
    public Order updateOrder(Order order) {
        return repo.save(order);
    }

    @Override
    public void deleteOrderById(Long id) {
        repo.deleteById(id);
    }
}
