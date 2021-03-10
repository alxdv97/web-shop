package ru.alxdv.nfoshop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.alxdv.nfoshop.dto.CustomerDTO;
import ru.alxdv.nfoshop.entity.Customer;
import ru.alxdv.nfoshop.mapper.CustomerMapper;
import ru.alxdv.nfoshop.repository.CustomerRepository;
import ru.alxdv.nfoshop.service.impl.DefaultCustomerService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultCustomerService.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    @MockBean
    private CustomerRepository repo;

    @MockBean
    private CustomerMapper mapper;

    private List<Customer> customerDB;

    @Before
    public void setUp(){

        CustomerDTO customerDTO = CustomerDTO.builder()
                .email("dto@email.com")
                .firstName("Data")
                .lastName("Transfer Object")
                .build();

        Customer customer1 = Customer.builder()
                .id(1L)
                .email("customer1@email.com")
                .address("Customer 1 Address")
                .firstName("Customer")
                .lastName("One")
                .phone("+11111111111")
                .build();

        Customer customer2 = Customer.builder()
                .id(2L)
                .email("customer2@email.com")
                .address("Customer 2 Address")
                .firstName("Customer")
                .lastName("Two")
                .phone("+22222222222")
                .build();

        Customer customer3 = Customer.builder()
                .id(3L)
                .email("customer3@email.com")
                .address("Customer 3 Address")
                .firstName("Customer")
                .lastName("Three")
                .phone("+33333333333")
                .build();

        when(mapper.toDTO(any())).thenReturn(customerDTO);
        when(mapper.toEntity(any())).thenReturn(customer1);
        doNothing().when(mapper).updateFromDtoToEntity(any(), any());

        customerDB = new ArrayList<>(List.of(customer1, customer2, customer3));

        when(repo.findAll()).thenReturn(customerDB);

        when(repo.getOne(anyLong())).thenAnswer(i -> {
            Long id = i.getArgument(0, Long.class);
            return getCustomerFromDB(id);
        });

        doAnswer(i -> {
            Long id = i.getArgument(0, Long.class);
            Customer customerFromDB = getCustomerFromDB(id);
            customerDB.remove(customerFromDB);
            return null;
        }).when(repo).deleteById(anyLong());

        when(repo.save(any(Customer.class))).thenAnswer(i -> {
            Customer customerToSave = i.getArgument(0, Customer.class);
            customerDB.add(customerToSave);
            return customerToSave;
        });
    }

    @Test
    public void getAllCustomersTest(){
        List<CustomerDTO> allCustomers = service.getAllCustomers();

        assertNotNull(allCustomers);
        assertEquals(3, allCustomers.size());
    }

    @Test
    public void createCustomerTest(){
        CustomerDTO customer4 = CustomerDTO.builder()
                .email("customer4@email.com")
                .address("Customer 4 Address")
                .firstName("Customer")
                .lastName("Four")
                .phone("+44444444444")
                .build();

        Long customerId = service.createCustomer(customer4);

        assertNotNull(customerId);
        assertEquals(4, customerDB.size());
    }

    @Test
    public void getCustomerTest(){
        CustomerDTO customer = service.getCustomer(1L);

        assertNotNull(customer);
        assertEquals("dto@email.com", customer.getEmail());
    }

    @Test
    public void updateCustomerTest(){
        Long updatedCustomerId = service.updateCustomer(CustomerDTO.builder()
                .email("customer3@email.comChanged")
                .address("Customer 3 Address Changed")
                .firstName("Customer Changed")
                .lastName("Three Changed")
                .phone("+33333333333")
                .build(), 3L);

        assertNotNull(updatedCustomerId);
        assertEquals(3L, updatedCustomerId.longValue());
    }

    @Test
    public void deleteCustomerTest(){
        service.deleteCustomerById(3L);

        assertEquals(2L, customerDB.size());
    }

    private Customer getCustomerFromDB(Long id){
        return customerDB.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .get();
    }
}
