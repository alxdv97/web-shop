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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.springframework.test.web.servlet.MvcResult;
import ru.alxdv.nfoshop.dto.CustomerDTO;
import ru.alxdv.nfoshop.entity.Customer;
import ru.alxdv.nfoshop.mapper.CustomerMapper;
import ru.alxdv.nfoshop.service.CustomerService;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerMapper mapper;

    @MockBean
    private CustomerService service;

    private CustomerDTO customerDTO;

    private String customer;

    @Before
    public void setUp() {
        customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setEmail("dto@email.com");
        customerDTO.setFirstName("Data");
        customerDTO.setLastName("Transfer Object");

        customer = "{\n" +
                "    \"email\": \"dto@email.com\",\n" +
                "    \"firstName\": \"Data\",\n" +
                "    \"lastName\": \"Transfer Object\",\n" +
                "    \"phone\": \"+00000000000\",\n" +
                "    \"address\": \"dtoAddress\"\n" +
                "}";

        when(mapper.toDTO(any())).thenReturn(customerDTO);
        when(mapper.toEntity(any())).thenReturn(new Customer());

        when(service.getAllCustomers()).thenReturn(List.of(new Customer(),
                new Customer(), new Customer()));

        when(service.getCustomer(anyLong())).thenReturn(new Customer());

        when(service.createCustomer(any(Customer.class))).thenReturn(new Customer());

        when(service.updateCustomer(any(Customer.class))).thenReturn(new Customer());

        doNothing().when(service).deleteCustomerById(anyLong());
    }

    @Test
    public void getAllCustomersTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/customers"))
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
                .andExpect(jsonPath("$.id").value(customerDTO.getId()))
                .andExpect(jsonPath("$.email").value(customerDTO.getEmail()))
                .andReturn();
    }

    @Test
    public void createCustomerTest() throws Exception {
        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(customer))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.id").value(customerDTO.getId()))
                    .andExpect(jsonPath("$.email").value(customerDTO.getEmail()))
                    .andReturn();
    }

    @Test
    public void updateCustomerTest() throws Exception {
        mockMvc.perform(put("/api/customers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(customer))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(customerDTO.getId()))
                .andExpect(jsonPath("$.email").value(customerDTO.getEmail()))
                .andReturn();
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        mockMvc.perform(delete("/api/customers/{id}","1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(customer))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

}
