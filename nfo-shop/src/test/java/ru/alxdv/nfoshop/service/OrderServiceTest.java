package ru.alxdv.nfoshop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.alxdv.nfoshop.dto.OrderDTO;
import ru.alxdv.nfoshop.entity.Customer;
import ru.alxdv.nfoshop.entity.Employee;
import ru.alxdv.nfoshop.entity.Order;
import ru.alxdv.nfoshop.entity.Product;
import ru.alxdv.nfoshop.mapper.OrderMapper;
import ru.alxdv.nfoshop.repository.EmployeeRepository;
import ru.alxdv.nfoshop.repository.OrderRepository;
import ru.alxdv.nfoshop.repository.ProductRepository;
import ru.alxdv.nfoshop.service.impl.DefaultOrderService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultOrderService.class)
public class OrderServiceTest {

    @Autowired
    private OrderService service;

    @MockBean
    private OrderRepository repo;

    @MockBean
    private ProductRepository productRepo;

    @MockBean
    private EmployeeRepository employeeRepo;

    @MockBean
    private OrderMapper mapper;

    private List<Order> orderDB;

    @Before
    public void setUp(){

        OrderDTO orderDTO = OrderDTO.builder()
                .customerId(1L)
                .employeeId(1L)
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryDate(Timestamp.valueOf(LocalDateTime.now().plusDays(2)))
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .email("customer@email.com")
                .address("Customer Address")
                .firstName("Customer")
                .lastName("One")
                .phone("+11111111111")
                .build();

        Employee employee = Employee.builder()
                .id(1L)
                .email("employee@email.com")
                .position("Employee Position")
                .firstName("Employee")
                .lastName("One")
                .phone("+11111111111")
                .build();

        Product product = Product.builder()
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

        when(mapper.toDTO(any())).thenReturn(orderDTO);
        when(mapper.toEntity(any())).thenReturn(order1);
        doNothing().when(mapper).updateFromDtoToEntity(any(), any());

        orderDB = new ArrayList<>(List.of(order1, order2, order3));

        when(repo.findAll()).thenReturn(orderDB);

        when(repo.getOne(anyLong())).thenAnswer(i -> {
            Long id = i.getArgument(0, Long.class);
            return getOrderFromDB(id);
        });

        doAnswer(i -> {
            Long id = i.getArgument(0, Long.class);
            Order orderFromDB = getOrderFromDB(id);
            orderDB.remove(orderFromDB);
            return null;
        }).when(repo).deleteById(anyLong());

        when(repo.save(any(Order.class))).thenAnswer(i -> {
            Order orderToSave = i.getArgument(0, Order.class);
            orderDB.add(orderToSave);
            return orderToSave;
        });

        when(employeeRepo.findAll()).thenReturn(List.of(employee));
        when(productRepo.getOne(anyLong())).thenReturn(product);
    }

    @Test
    public void getAllOrdersTest(){
        List<OrderDTO> allOrders = service.getAllOrders();

        assertNotNull(allOrders);
        assertEquals(3, allOrders.size());
    }

    @Test
    public void createOrderTest(){
        OrderDTO order4 = OrderDTO.builder()
                .customerId(1L)
                .employeeId(1L)
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryDate(Timestamp.valueOf(LocalDateTime.now().plusDays(2)))
                .build();

        Long orderId = service.createOrder(order4);

        assertNotNull(orderId);
        assertEquals(4, orderDB.size());
    }

    @Test
    public void getOrderTest(){
        OrderDTO order = service.getOrder(1L);

        assertNotNull(order);
        assertEquals(1L, order.getCustomerId().longValue());
    }

    @Test
    public void updateOrderTest(){
        Long updatedOrderId = service.updateOrder(OrderDTO.builder()
                .customerId(4L)
                .employeeId(4L)
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryDate(Timestamp.valueOf(LocalDateTime.now().plusDays(2)))
                .build(), 3L);

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
        OrderDTO orderWithoutEmployee = OrderDTO.builder().build();

        OrderDTO orderWithEmployee = service.assignEmployeeToOrder(orderWithoutEmployee);

        assertNotNull(orderWithEmployee.getEmployeeId());

    }

    private Order getOrderFromDB(Long id){
        return orderDB.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .get();
    }
}
