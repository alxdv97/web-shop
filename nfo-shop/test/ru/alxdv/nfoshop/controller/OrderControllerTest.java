package ru.alxdv.nfoshop.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.alxdv.nfoshop.dto.OrderDTO;
import ru.alxdv.nfoshop.entity.Customer;
import ru.alxdv.nfoshop.entity.Employee;
import ru.alxdv.nfoshop.entity.Order;
import ru.alxdv.nfoshop.entity.Product;
import ru.alxdv.nfoshop.mapper.OrderMapper;
import ru.alxdv.nfoshop.service.OrderService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderMapper mapper;

    @MockBean
    private OrderService service;

    private OrderDTO orderDTO;

    private String orderJson;

    @Before
    public void setUp() {
        orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setEmployeeId(1L);
        orderDTO.setCustomerId(1L);


        orderJson = "{\n" +
                "    \"customerId\": 1,\n" +
                "    \"employeeId\": 1\n" +
                "}";

        Order order = Order.builder()
                .id(1L)
                .customer(Customer.builder().id(1L).build())
                .employee(Employee.builder().id(1L).build())
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryDate(Timestamp.valueOf(LocalDateTime.now().plusDays(2)))
                .products(Set.of(Product.builder().build()))
                .build();


        when(mapper.toDTO(any())).thenReturn(orderDTO);
        when(mapper.toEntity(any())).thenReturn(order);

        when(service.getAllOrders()).thenReturn(List.of(order,
                order, order));

        when(service.getOrder(anyLong())).thenReturn(order);

        when(service.createOrder(any(Order.class))).thenReturn(order.getId());

        when(service.updateOrder(any(Order.class))).thenReturn(order.getId());

        doNothing().when(service).deleteOrderById(anyLong());
    }

    @Test
    public void getAllOrdersTest() throws Exception {
        mockMvc.perform(get("/api/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("[*]").isArray())
                .andReturn();
    }

    @Test
    public void getOrderTest() throws Exception {
        mockMvc.perform(get("/api/orders/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(orderDTO.getId()))
                .andReturn();
    }

    @Test
    public void createOrderTest() throws Exception {
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(orderJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(orderDTO.getId().toString()))
                .andReturn();
    }

    @Test
    public void updateOrderTest() throws Exception {
        mockMvc.perform(put("/api/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(orderJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(orderDTO.getId().toString()))
                .andReturn();
    }

    @Test
    public void deleteOrderTest() throws Exception {
        mockMvc.perform(delete("/api/orders/{id}","1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(orderJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
