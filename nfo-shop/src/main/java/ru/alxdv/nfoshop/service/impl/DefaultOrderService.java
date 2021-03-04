package ru.alxdv.nfoshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alxdv.nfoshop.entity.Employee;
import ru.alxdv.nfoshop.entity.Order;
import ru.alxdv.nfoshop.entity.Product;
import ru.alxdv.nfoshop.repository.EmployeeRepository;
import ru.alxdv.nfoshop.repository.OrderRepository;
import ru.alxdv.nfoshop.repository.ProductRepository;
import ru.alxdv.nfoshop.service.OrderService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class DefaultOrderService implements OrderService {

    private static final Integer DELIVERY_TIME_DAYS = 2;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private ProductRepository productRepo;

    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @Override
    public Long createOrder(Order order) {
        order.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        order.setDeliveryDate(Timestamp.valueOf(LocalDateTime.now().plusDays(DELIVERY_TIME_DAYS)));

        return orderRepo.save(assignEmployeeToOrder(order)).getId();
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepo.getOne(id);
    }

    @Override
    public Long updateOrder(Order order) {
        return orderRepo.save(order).getId();
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepo.deleteById(id);
    }

    @Override
    public Order addProductToOrder(Long orderId, Long productId) {
        Product product = productRepo.getOne(productId);
        Order order = orderRepo.getOne(orderId);
        order.addProduct(product);
        return orderRepo.save(order);
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long id) {
        return orderRepo.findOrdersByCustomerId(id);
    }

    //Modeling assigning-to-employee process
    @Override
    public Order assignEmployeeToOrder(Order order) {
        List<Employee> allEmployees = employeeRepo.findAll();
        Random rand = new Random();
        order.setEmployee(allEmployees.get(rand.nextInt(allEmployees.size())));
        return order;
    }
}
