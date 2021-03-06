package ru.alxdv.nfoshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.alxdv.nfoshop.dto.OrderDTO;
import ru.alxdv.nfoshop.entity.Employee;
import ru.alxdv.nfoshop.entity.Order;
import ru.alxdv.nfoshop.entity.Product;
import ru.alxdv.nfoshop.exception.NfoException;
import ru.alxdv.nfoshop.mapper.OrderMapper;
import ru.alxdv.nfoshop.repository.EmployeeRepository;
import ru.alxdv.nfoshop.repository.OrderRepository;
import ru.alxdv.nfoshop.repository.ProductRepository;
import ru.alxdv.nfoshop.service.OrderService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DefaultOrderService implements OrderService {

    private static final Integer DELIVERY_TIME_DAYS = 2;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepo.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long createOrder(OrderDTO order) {
        order.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        order.setDeliveryDate(Timestamp.valueOf(LocalDateTime.now().plusDays(DELIVERY_TIME_DAYS)));
        assignEmployeeToOrder(order);
        return orderRepo.save(mapper.toEntity(order)).getId();
    }

    @Override
    public OrderDTO getOrder(Long id) {
        return mapper.toDTO(orderRepo.getOne(id));
    }

    @Override
    public Long updateOrder(OrderDTO order, Long id) {
        Order orderFromDb = orderRepo.getOne(id);
        mapper.updateFromDtoToEntity(order, orderFromDb);
        return orderRepo.save(orderFromDb).getId();
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepo.deleteById(id);
    }

    @Override
    public OrderDTO addProductToOrder(Long orderId, Long productId) {
        Product product = productRepo.getOne(productId);
        Order order = orderRepo.getOne(orderId);
        order.addProduct(product);
        return mapper.toDTO(orderRepo.save(order));
    }

    @Override
    public List<OrderDTO> getOrdersByCustomerId(Long id) {
        return orderRepo.findOrdersByCustomerId(id)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    //Modeling assigning-to-employee process
    private void assignEmployeeToOrder(OrderDTO order) {
        List<Employee> allEmployees = employeeRepo.findAll();
        if (allEmployees.isEmpty()){
            throw new NfoException("Cannot assign employee to order! No employee found.");
        }
        Random rand = new Random();
        order.setEmployeeId(allEmployees.get(rand.nextInt(allEmployees.size())).getId());
    }

    @Override
    public void sendMessage(String msg){
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send("order", msg);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + msg +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + msg + "] due to : " + ex.getMessage());
            }
        });
    }
}
