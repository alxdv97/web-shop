package ru.alxdv.nfoshop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.alxdv.nfoshop.entity.Customer;
import ru.alxdv.nfoshop.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    @MockBean
    private CustomerRepository repo;

    private List<Customer> customerDB;

    @Before
    public void setUp(){
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

        customerDB = new ArrayList<>();
        customerDB.add(customer1);
        customerDB.add(customer2);
        customerDB.add(customer3);
    }

    @Test
    public void getAllCustomersTest(){
        when(repo.findAll()).thenReturn(customerDB);

        List<Customer> allCustomers = service.getAllCustomers();

        assertNotNull(allCustomers);
        assertEquals(3L, allCustomers.size());
    }

    @Test
    public void createCustomerTest(){
        when(repo.save(any(Customer.class))).thenAnswer(i -> {
            Customer customer = i.getArgument(0, Customer.class);
            customerDB.add(customer);
            return customer;
        });

        Customer customer4 = Customer.builder()
                .id(4L)
                .email("customer4@email.com")
                .address("Customer 4 Address")
                .firstName("Customer")
                .lastName("Four")
                .phone("+44444444444")
                .build();

        Customer customer = service.createCustomer(customer4);

        assertEquals(4L, customerDB.size());
    }

    @Test
    public void getCustomerTest(){
        when(repo.getOne(anyLong())).thenAnswer(i -> {
            Long id = i.getArgument(0, Long.class);
            return getCustomerFromDB(id);
        });

        Customer customer = service.getCustomer(1L);

        assertNotNull(customer);
        assertEquals(1L, customer.getId().longValue());
    }

    @Test
    public void updateCustomerTest(){
        when(repo.save(any(Customer.class))).thenAnswer(i -> {
            Customer customer = i.getArgument(0, Customer.class);
            Customer customerFromDB = getCustomerFromDB(customer.getId());

            customerDB.remove(customerFromDB);

            customerFromDB.setEmail(customer.getEmail());
            customerFromDB.setAddress(customer.getAddress());
            customerFromDB.setFirstName(customer.getFirstName());
            customerFromDB.setLastName(customer.getLastName());
            customerFromDB.setLastName(customer.getLastName());
            customerFromDB.setPhone(customer.getPhone());

            customerDB.add(customerFromDB);

            return customerFromDB;
        });

        Customer updatedCustomer = service.updateCustomer(Customer.builder()
                .id(3L)
                .email("customer3@email.comChanged")
                .address("Customer 3 Address Changed")
                .firstName("Customer Changed")
                .lastName("Three Changed")
                .phone("+33333333333")
                .build());

        assertNotNull(updatedCustomer);
        assertEquals("customer3@email.comChanged", updatedCustomer.getEmail());
    }

    @Test
    public void deleteCustomerTest(){
        doAnswer(i -> {
            Long id = i.getArgument(0, Long.class);
            Customer customer = getCustomerFromDB(id);
            customerDB.remove(customer);
            return null;
        }).when(repo).deleteById(anyLong());

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
