package ru.alxdv.nfoshop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.alxdv.nfoshop.entity.Employee;
import ru.alxdv.nfoshop.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService service;

    @MockBean
    private EmployeeRepository repo;

    private List<Employee> employeeDB;

    @Before
    public void setUp(){
        Employee employee1 = Employee.builder()
                .id(1L)
                .email("employee1@email.com")
                .position("Employee 1 Position")
                .firstName("Employee")
                .lastName("One")
                .phone("+11111111111")
                .build();

        Employee employee2 = Employee.builder()
                .id(2L)
                .email("employee1@email.com")
                .position("Employee 1 Position")
                .firstName("Employee")
                .lastName("Two")
                .phone("+22222222222")
                .build();

        Employee employee3 = Employee.builder()
                .id(3L)
                .email("employee1@email.com")
                .position("Employee 1 Position")
                .firstName("Employee")
                .lastName("Three")
                .phone("+33333333333")
                .build();

        employeeDB = new ArrayList<>(List.of(employee1, employee2, employee3));

        when(repo.findAll()).thenReturn(employeeDB);

        when(repo.getOne(anyLong())).thenAnswer(i -> {
            Long id = i.getArgument(0, Long.class);
            return getEmployeeFromDB(id);
        });

        doAnswer(i -> {
            Long id = i.getArgument(0, Long.class);
            Employee employee = getEmployeeFromDB(id);
            employeeDB.remove(employee);
            return null;
        }).when(repo).deleteById(anyLong());

        when(repo.save(any(Employee.class))).thenAnswer(i -> {
            Employee employee = i.getArgument(0, Employee.class);
            employeeDB.add(employee);
            return employee;
        });
    }

    @Test
    public void getAllEmployeesTest(){
        List<Employee> allEmployees = service.getAllEmployees();

        assertNotNull(allEmployees);
        assertEquals(3, allEmployees.size());
    }

    @Test
    public void createEmployeeTest(){
        Employee employee4 = Employee.builder()
                .id(4L)
                .email("employee4@email.com")
                .position("Employee 4 Address")
                .firstName("Employee")
                .lastName("Four")
                .phone("+44444444444")
                .build();

        Employee employee = service.createEmployee(employee4);

        assertNotNull(employee);
        assertEquals(4, employeeDB.size());
    }

    @Test
    public void getEmployeeTest(){
        Employee employee = service.getEmployee(1L);

        assertNotNull(employee);
        assertEquals(1L, employee.getId().longValue());
    }

    @Test
    public void updateEmployeeTest(){
        Employee updatedEmployee = service.updateEmployee(Employee.builder()
                .id(3L)
                .email("emplioyee3@email.comChanged")
                .position("Employee 3 Address Changed")
                .firstName("Employee Changed")
                .lastName("Three Changed")
                .phone("+33333333333")
                .build());

        assertNotNull(updatedEmployee);
        assertEquals("emplioyee3@email.comChanged", updatedEmployee.getEmail());
    }

    @Test
    public void deleteEmployeeTest(){
        service.deleteEmployeeById(3L);

        assertEquals(2, employeeDB.size());
    }

    private Employee getEmployeeFromDB(Long id){
        return employeeDB.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .get();
    }
}
