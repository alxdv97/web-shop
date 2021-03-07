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
import ru.alxdv.nfoshop.feign.UserAuthService;
import ru.alxdv.nfoshop.interceptor.AuthHandlerInterceptor;
import ru.alxdv.nfoshop.service.EmployeeService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    private EmployeeService service;

    @MockBean
    private UserAuthService authService;

    @MockBean
    private AuthHandlerInterceptor authHandlerInterceptor;

    private EmployeeDTO employee;

    private String employeeJson;

    private Long employeeId;

    @Before
    public void setUp() {
        employeeJson = "{\n" +
                "    \"email\": \"dto@email.com\",\n" +
                "    \"firstName\": \"Data\",\n" +
                "    \"lastName\": \"Transfer Object\",\n" +
                "    \"phone\": \"+00000000000\",\n" +
                "    \"position\": \"dtoPosition\"\n" +
                "}";

        employee = EmployeeDTO.builder()
                .email("dto@email.com")
                .firstName("Data")
                .lastName("Transfer Object")
                .phone("+00000000000")
                .position("dtoPosition")
                .build();

        employeeId = 1L;

        when(authService.auth(anyString())).thenReturn(Boolean.TRUE);

        when(authHandlerInterceptor.preHandle(any(), any(), any())).thenReturn(Boolean.TRUE);

        when(service.getAllEmployees()).thenReturn(List.of(employee,
                employee, employee));

        when(service.getEmployee(anyLong())).thenReturn(employee);

        when(service.createEmployee(any(EmployeeDTO.class))).thenReturn(employeeId);

        when(service.updateEmployee(any(EmployeeDTO.class), anyLong())).thenReturn(employeeId);

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
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
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
                .andExpect(content().string(employeeId.toString()))
                .andReturn();
    }

    @Test
    public void updateEmployeeTest() throws Exception {
        mockMvc.perform(put("/api/employees?employeeId=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(employeeId.toString()))
                .andReturn();
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        mockMvc.perform(delete("/api/employees/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
