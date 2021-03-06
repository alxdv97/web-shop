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
import ru.alxdv.nfoshop.dto.ProductDTO;
import ru.alxdv.nfoshop.feign.UserAuthService;
import ru.alxdv.nfoshop.interceptor.AuthHandlerInterceptor;
import ru.alxdv.nfoshop.service.OrderService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
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
    private OrderService service;

    @MockBean
    private UserAuthService authService;

    @MockBean
    private AuthHandlerInterceptor authHandlerInterceptor;

    private OrderDTO order;

    private String orderJson;

    private Long orderId;

    @Before
    public void setUp() {

        orderJson = "{\n" +
                "    \"customerId\": 1,\n" +
                "    \"employeeId\": 1\n" +
                "}";

        order = OrderDTO.builder()
                .customerId(1L)
                .employeeId(1L)
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryDate(Timestamp.valueOf(LocalDateTime.now().plusDays(2)))
                .products(Set.of(ProductDTO.builder().build()))
                .build();

        orderId = 1L;

        when(authService.auth(anyString())).thenReturn(Boolean.TRUE);

        when(authHandlerInterceptor.preHandle(any(), any(), any())).thenReturn(Boolean.TRUE);

        when(service.getAllOrders()).thenReturn(List.of(order,
                order, order));

        when(service.getOrder(anyLong())).thenReturn(order);

        when(service.createOrder(any(OrderDTO.class))).thenReturn(orderId);

        when(service.updateOrder(any(OrderDTO.class), anyLong())).thenReturn(orderId);

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
                .andExpect(jsonPath("$.customerId").value(order.getCustomerId()))
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
                .andExpect(content().string(orderId.toString()))
                .andReturn();
    }

    @Test
    public void updateOrderTest() throws Exception {
        mockMvc.perform(put("/api/orders?orderId=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(orderJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(orderId.toString()))
                .andReturn();
    }

    @Test
    public void deleteOrderTest() throws Exception {
        mockMvc.perform(delete("/api/orders/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(orderJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
