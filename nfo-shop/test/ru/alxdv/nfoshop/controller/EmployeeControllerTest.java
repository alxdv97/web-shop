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
import ru.alxdv.nfoshop.dto.EmployeeDTO;
import ru.alxdv.nfoshop.entity.Employee;
import ru.alxdv.nfoshop.mapper.EmployeeMapper;
import ru.alxdv.nfoshop.service.EmployeeService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeMapper mapper;

    @MockBean
    private EmployeeService service;

    private EmployeeDTO employeeDTO;

    private String employeeJson;

    @Before
    public void setUp() {
        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setEmail("dto@email.com");
        employeeDTO.setFirstName("Data");
        employeeDTO.setLastName("Transfer Object");

        employeeJson = "{\n" +
                "    \"email\": \"dto@email.com\",\n" +
                "    \"firstName\": \"Data\",\n" +
                "    \"lastName\": \"Transfer Object\",\n" +
                "    \"phone\": \"+00000000000\",\n" +
                "    \"position\": \"dtoPosition\"\n" +
                "}";

        Employee employee = Employee.builder()
                .id(1L)
                .email("dto@email.com")
                .firstName("Data")
                .lastName("Transfer Object")
                .phone("+00000000000")
                .position("dtoPosition")
                .build();

        when(mapper.toDTO(any())).thenReturn(employeeDTO);
        when(mapper.toEntity(any())).thenReturn(employee);

        when(service.getAllEmployees()).thenReturn(List.of(employee,
                employee, employee));

        when(service.getEmployee(anyLong())).thenReturn(employee);

        when(service.createEmployee(any(Employee.class))).thenReturn(employee);

        when(service.updateEmployee(any(Employee.class))).thenReturn(employee);

        doNothing().when(service).deleteEmployeeById(anyLong());
    }

    @Test
    public void getAllEmployeesTest() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("[*]").isArray())
                .andReturn();
    }

    @Test
    public void getEmployeeTest() throws Exception {
        mockMvc.perform(get("/api/employees/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(employeeDTO.getId()))
                .andReturn();
    }

    @Test
    public void createEmployeeTest() throws Exception {
        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(employeeDTO.getId().toString()))
                .andReturn();
    }

    @Test
    public void updateEmployeeTest() throws Exception {
        mockMvc.perform(put("/api/employees")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(employeeDTO.getId().toString()))
                .andReturn();
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        mockMvc.perform(delete("/api/employees/{id}","1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
