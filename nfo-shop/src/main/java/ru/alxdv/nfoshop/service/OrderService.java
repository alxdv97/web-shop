package ru.alxdv.nfoshop.service;

import ru.alxdv.nfoshop.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order createOrder(Order order);
    Order getOrder(Long id);
    Order updateOrder(Order order);
    void deleteOrderById(Long id);
    Order addProductToOrder(Long orderId, Long productId);
    List<Order> getOrdersByCustomerId(Long id);
}
