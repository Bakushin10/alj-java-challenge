package jp.co.axa.apidemo.controller;

import jp.co.axa.apidemo.controllers.EmployeeController;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class EmployeeControllerTest {

    @InjectMocks
    EmployeeController employeeController;

    @Mock
    EmployeeService employeeService;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should return employee object")
    void  shouldReturnEmployeeObject(){
        Employee employee = new Employee(
                1L,
                "John Oliver",
                10000,
                "TECH"
        );

        List<Employee> mockEmployee = new ArrayList<>();
        mockEmployee.add(employee);
        when(employeeController.getEmployees()).thenReturn(mockEmployee);
        employeeController.saveEmployee(employee);
        List<Employee> response = employeeController.getEmployees();
        Assertions.assertEquals(response, mockEmployee);

        // cache write through and store the object into cache
        List<Employee> responseFromCache = employeeController.getEmployees();
        Assertions.assertEquals(responseFromCache, mockEmployee);
    }

    @Test
    @DisplayName("should return employee object with given id")
    void  shouldReturnEmployeeObjectWithGivenID(){
        Employee employee = new Employee(
                1L,
                "John Oliver",
                10000,
                "TECH"
        );

        when(employeeController.getEmployee(4L)).thenReturn(employee);

        // cache write through and store the object into cache
        Employee response = employeeController.getEmployee(4L);
        Assertions.assertEquals(response, employee);

        // this covers the lines that check the caches
        Employee responseFromCache = employeeController.getEmployee(4L);
        Assertions.assertEquals(responseFromCache, employee);
    }

}
