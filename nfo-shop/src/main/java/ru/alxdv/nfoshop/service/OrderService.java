package ru.alxdv.nfoshop.service;

import ru.alxdv.nfoshop.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Long createOrder(Order order);
    Order getOrder(Long id);
    Long updateOrder(Order order);
    void deleteOrderById(Long id);
    Order addProductToOrder(Long orderId, Long productId);
    List<Order> getOrdersByCustomerId(Long id);
    Order assignEmployeeToOrder(Order order);
}
