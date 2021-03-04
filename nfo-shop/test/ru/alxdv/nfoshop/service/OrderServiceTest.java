package ru.alxdv.nfoshop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.alxdv.nfoshop.entity.Customer;
import ru.alxdv.nfoshop.entity.Employee;
import ru.alxdv.nfoshop.entity.Order;
import ru.alxdv.nfoshop.entity.Product;
import ru.alxdv.nfoshop.repository.EmployeeRepository;
import ru.alxdv.nfoshop.repository.OrderRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService service;

    @MockBean
    private OrderRepository repo;

    @MockBean
    private EmployeeRepository employeeRepo;

    private List<Order> orderDB;

    private Customer customer;

    private Employee employee;

    private Product product;

    @Before
    public void setUp(){

        customer = Customer.builder()
                .id(1L)
                .email("customer@email.com")
                .address("Customer Address")
                .firstName("Customer")
                .lastName("One")
                .phone("+11111111111")
                .build();

        employee = Employee.builder()
                .id(1L)
                .email("employee@email.com")
                .position("Employee Position")
                .firstName("Employee")
                .lastName("One")
                .phone("+11111111111")
                .build();

        product = Product.builder()
                .id(1L)
                .name("Product")
                .description("Product description")
                .price(10.1)
                .build();

        Order order1 = Order.builder()
                .id(1L)
                .customer(customer)
                .employee(employee)
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryDate(Timestamp.valueOf(LocalDateTime.now().plusDays(2)))
                .products(Set.of(product))
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .customer(customer)
                .employee(employee)
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryDate(Timestamp.valueOf(LocalDateTime.now().plusDays(2)))
                .products(Set.of(product))
                .build();

        Order order3 = Order.builder()
                .id(3L)
                .customer(customer)
                .employee(employee)
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryDate(Timestamp.valueOf(LocalDateTime.now().plusDays(2)))
                .products(Set.of(product))
                .build();

        orderDB = new ArrayList<>(List.of(order1, order2, order3));

        when(repo.findAll()).thenReturn(orderDB);

        when(repo.getOne(anyLong())).thenAnswer(i -> {
            Long id = i.getArgument(0, Long.class);
            return getOrderFromDB(id);
        });

        doAnswer(i -> {
            Long id = i.getArgument(0, Long.class);
            Order order = getOrderFromDB(id);
            orderDB.remove(order);
            return null;
        }).when(repo).deleteById(anyLong());

        when(repo.save(any(Order.class))).thenAnswer(i -> {
            Order order = i.getArgument(0, Order.class);
            orderDB.add(order);
            return order;
        });

        when(employeeRepo.findAll()).thenReturn(List.of(new Employee()));
    }

    @Test
    public void getAllOrdersTest(){
        List<Order> allOrders = service.getAllOrders();

        assertNotNull(allOrders);
        assertEquals(3, allOrders.size());
    }

    @Test
    public void createOrderTest(){
        Order order4 = Order.builder()
                .id(4L)
                .customer(customer)
                .employee(employee)
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryDate(Timestamp.valueOf(LocalDateTime.now().plusDays(2)))
                .products(Set.of(product))
                .build();

        Long orderId = service.createOrder(order4);

        assertNotNull(orderId);
        assertEquals(4, orderDB.size());
    }

    @Test
    public void getOrderTest(){
        Order order = service.getOrder(1L);

        assertNotNull(order);
        assertEquals(1L, order.getId().longValue());
    }

    @Test
    public void updateOrderTest(){
        Long updatedOrderId = service.updateOrder(Order.builder()
                .id(3L)
                .customer(new Customer())
                .employee(new Employee())
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryDate(Timestamp.valueOf(LocalDateTime.now().plusDays(2)))
                .products(Set.of(product))
                .build());

        assertNotNull(updatedOrderId);
        assertEquals(3L, updatedOrderId.longValue());
    }

    @Test
    public void deleteOrderTest(){
        service.deleteOrderById(3L);

        assertEquals(2L, orderDB.size());
    }

    @Test
    public void assignEmployeeToOrderTest(){
        Order orderWithoutEmployee = Order.builder().build();

        Order orderWithEmployee = service.assignEmployeeToOrder(orderWithoutEmployee);

        assertNotNull(orderWithEmployee.getEmployee());

    }

    private Order getOrderFromDB(Long id){
        return orderDB.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .get();
    }
}
