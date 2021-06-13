package ru.alxdv.nfoshop.service;

import ru.alxdv.nfoshop.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();
    Long createOrder(OrderDTO order);
    OrderDTO getOrder(Long id);
    Long updateOrder(OrderDTO order, Long id);
    void deleteOrderById(Long id);
    OrderDTO addProductToOrder(Long orderId, Long productId);
    List<OrderDTO> getOrdersByCustomerId(Long id);
    void sendMessage(String msg);
}
