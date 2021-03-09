package ru.alxdv.nfoshop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.alxdv.nfoshop.dto.EmployeeDTO;
import ru.alxdv.nfoshop.entity.Employee;
import ru.alxdv.nfoshop.mapper.EmployeeMapper;
import ru.alxdv.nfoshop.repository.EmployeeRepository;
import ru.alxdv.nfoshop.service.impl.DefaultEmployeeService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultEmployeeService.class)
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService service;

    @MockBean
    private EmployeeRepository repo;

    @MockBean
    private EmployeeMapper mapper;

    private List<Employee> employeeDB;

    @Before
    public void setUp(){

        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .email("dto@email.com")
                .firstName("Data")
                .lastName("Transfer Object")
                .build();

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

        when(mapper.toDTO(any())).thenReturn(employeeDTO);
        when(mapper.toEntity(any())).thenReturn(employee1);
        doNothing().when(mapper).updateFromDtoToEntity(any(), any());

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
            Employee employeeToSave = i.getArgument(0, Employee.class);
            employeeDB.add(employeeToSave);
            return employeeToSave;
        });
    }

    @Test
    public void getAllEmployeesTest(){
        List<EmployeeDTO> allEmployees = service.getAllEmployees();

        assertNotNull(allEmployees);
        assertEquals(3, allEmployees.size());
    }

    @Test
    public void createEmployeeTest(){
        EmployeeDTO employee4 = EmployeeDTO.builder()
                .email("employee4@email.com")
                .position("Employee 4 Address")
                .firstName("Employee")
                .lastName("Four")
                .phone("+44444444444")
                .build();

        Long employeeId = service.createEmployee(employee4);

        assertNotNull(employeeId);
        assertEquals(4, employeeDB.size());
    }

    @Test
    public void getEmployeeTest(){
        EmployeeDTO employee = service.getEmployee(1L);

        assertNotNull(employee);
        assertEquals("dto@email.com", employee.getEmail());
    }

    @Test
    public void updateEmployeeTest(){
        Long updatedEmployeeId = service.updateEmployee(EmployeeDTO.builder()
                .email("emplioyee3@email.comChanged")
                .position("Employee 3 Address Changed")
                .firstName("Employee Changed")
                .lastName("Three Changed")
                .phone("+33333333333")
                .build(), 3L);

        assertNotNull(updatedEmployeeId);
        assertEquals(3L, updatedEmployeeId.longValue());
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
