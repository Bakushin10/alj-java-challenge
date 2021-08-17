package jp.co.axa.apidemo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    // cache request. preferably use some external caching service that supports object caching
    Map cache = new HashMap<String, Object>();

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        logger.info("==== GET /employees");

        if (cache.get("all") != null){
            logger.info("==== GET /employees cache hit : key = 'all' ! ");
            return (List<Employee>) cache.get("all");
        }
        List<Employee> employees = employeeService.retrieveEmployees();
        cache.put("all", employees);
        return employees;
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        logger.info("==== GET /employees/" + employeeId);

        if(cache.get(employeeId) != null){
            logger.info("==== GET /employees/" + employeeId + " cache hit : key = " + employeeId);
            return (Employee) cache.get(employeeId);
        }
        Employee employee = employeeService.getEmployee(employeeId);
        cache.put(employeeId, employee);
        return employee;
    }

    @PostMapping("/employees")
    public void saveEmployee(Employee employee){
        logger.info("==== POST /employees" + employee);
        employeeService.saveEmployee(employee);

        // since new item is updated, clear the cache.
        // ideally, we should use materialized view to only fetch the updated items
        cache.put("all", null);
        System.out.println("Employee Saved Successfully");
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        logger.info("==== POST /employees/" + employeeId);
        employeeService.deleteEmployee(employeeId);
        System.out.println("Employee Deleted Successfully");

        // remove item from the cache
        if(cache.get(employeeId) != null){
            cache.put(employeeId, null);
        }
    }

    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId")Long employeeId){
        logger.info("==== PUT /employees/" + employeeId);
        Employee emp = employeeService.getEmployee(employeeId);
        if(emp != null){
            employeeService.updateEmployee(employee);
        }

        // clear the cache or we can make another http call tp fetch and update the cache
        if(cache.get(employeeId) != null){
            cache.put(employeeId, null);
        }
    }

}