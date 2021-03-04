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
import ru.alxdv.nfoshop.entity.Customer;
import ru.alxdv.nfoshop.mapper.CustomerMapper;
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
    private CustomerMapper mapper;

    @MockBean
    private CustomerService service;

    private CustomerDTO customerDTO;

    private String customerJson;

    @Before
    public void setUp() {
        customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setEmail("dto@email.com");
        customerDTO.setFirstName("Data");
        customerDTO.setLastName("Transfer Object");

        customerJson = "{\n" +
                "    \"email\": \"dto@email.com\",\n" +
                "    \"firstName\": \"Data\",\n" +
                "    \"lastName\": \"Transfer Object\",\n" +
                "    \"phone\": \"+00000000000\",\n" +
                "    \"address\": \"dtoAddress\"\n" +
                "}";

        Customer customer = Customer.builder()
                .id(1L)
                .email("dto@email.com")
                .firstName("Data")
                .lastName("Transfer Object")
                .phone("+00000000000")
                .address("dtoAddress")
                .build();

        when(mapper.toDTO(any())).thenReturn(customerDTO);
        when(mapper.toEntity(any())).thenReturn(customer);

        when(service.getAllCustomers()).thenReturn(List.of(customer,
                customer, customer));

        when(service.getCustomer(anyLong())).thenReturn(customer);

        when(service.createCustomer(any(Customer.class))).thenReturn(customer.getId());

        when(service.updateCustomer(any(Customer.class))).thenReturn(customer.getId());

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
                .andExpect(jsonPath("$.id").value(customerDTO.getId()))
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
                    .andExpect(content().string(customerDTO.getId().toString()))
                    .andReturn();
    }

    @Test
    public void updateCustomerTest() throws Exception {
        mockMvc.perform(put("/api/customers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(customerJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(customerDTO.getId().toString()))
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
