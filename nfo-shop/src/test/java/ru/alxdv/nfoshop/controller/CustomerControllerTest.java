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
import ru.alxdv.nfoshop.dto.CustomerDTO;
import ru.alxdv.nfoshop.feign.UserAuthService;
import ru.alxdv.nfoshop.interceptor.AuthHandlerInterceptor;
import ru.alxdv.nfoshop.service.CustomerService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    @MockBean
    private UserAuthService authService;

    @MockBean
    private AuthHandlerInterceptor authHandlerInterceptor;

    private CustomerDTO customer;

    private String customerJson;

    private Long customerId;

    @Before
    public void setUp() {

        customerJson = "{\n" +
                "    \"email\": \"dto@email.com\",\n" +
                "    \"firstName\": \"Data\",\n" +
                "    \"lastName\": \"Transfer Object\",\n" +
                "    \"phone\": \"+00000000000\",\n" +
                "    \"address\": \"dtoAddress\"\n" +
                "}";

        customer = CustomerDTO.builder()
                .email("dto@email.com")
                .firstName("Data")
                .lastName("Transfer Object")
                .phone("+00000000000")
                .address("dtoAddress")
                .build();

        customerId = 1L;

        when(authService.auth(anyString())).thenReturn(Boolean.TRUE);

        when(authHandlerInterceptor.preHandle(any(), any(), any())).thenReturn(Boolean.TRUE);

        when(service.getAllCustomers()).thenReturn(List.of(customer,
                customer, customer));

        when(service.getCustomer(anyLong())).thenReturn(customer);

        when(service.createCustomer(any(CustomerDTO.class))).thenReturn(customerId);

        when(service.updateCustomer(any(CustomerDTO.class), anyLong())).thenReturn(customerId);

        doNothing().when(service).deleteCustomerById(anyLong());
    }

    @Test
    public void getAllCustomersTest() throws Exception {
        mockMvc.perform(get("/api/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("[*]").isArray())
                .andReturn();
    }

    @Test
    public void getCustomerTest() throws Exception {
        mockMvc.perform(get("/api/customers/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andReturn();
    }

    @Test
    public void createCustomerTest() throws Exception {
        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(customerJson))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(content().string(customerId.toString()))
                    .andReturn();
    }

    @Test
    public void updateCustomerTest() throws Exception {
        mockMvc.perform(put("/api/customers?customerId=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(customerJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(customerId.toString()))
                .andReturn();
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        mockMvc.perform(delete("/api/customers/{id}","1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(customerJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
