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
    public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId) throws Exception {
        logger.info("==== GET /employees/" + employeeId);

        if(cache.get(employeeId) != null){
            logger.info("==== GET /employees/" + employeeId + " cache hit : key = " + employeeId);
            return (Employee) cache.get(employeeId);
        }

        try {
            Employee employee = employeeService.getEmployee(employeeId);
            cache.put(employeeId, employee);
            return employee;
        }catch (Exception e){
            logger.info("==== GET employeeId '" + employeeId + "' was not in the record");
            throw new Exception("Employee updated failed : " + e);
        }
    }

    @PostMapping("/employees")
    public void saveEmployee(@RequestBody Employee employee) throws Exception {
        logger.info("==== POST /employees" + employee);

        // since new item is updated, clear the cache.
        // ideally, we should use materialized view to only fetch the updated items
        cache.put("all", null);
        Long employeeId = employee.getId();
        try {
            System.out.println("employeeId : " + employeeService.hasEmployeeId(employeeId));
            if(!employeeService.hasEmployeeId(employeeId)){
                Employee emp = employeeService.saveEmployee(employee);
                System.out.println("saved employee : " + emp);
                if(emp != null) {
                    cache.put(employeeId, employee);
                }
                // set cache as null
                cache.put("all", null);
                logger.info("==== POST employeeId '" + employeeId + "' was saved successfully");
            }else{
                logger.info("==== POST [error] employeeId '" + employeeId + "' is already in the record");
            }
        }catch (Exception e) {
            logger.info("==== POST [error] employeeId '" + employeeId + " " + e);
            throw new Exception("Employee updated failed : " + e);
        }
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId) throws Exception{
        logger.info("==== DELETE /employees/" + employeeId);

        try {
            if(employeeService.hasEmployeeId(employeeId)){
                employeeService.deleteEmployee(employeeId);
                if(cache.get(employeeId) != null) {
                    cache.remove(employeeId);
                }

                // set cache as null
                cache.put("all", null);
                logger.info("==== DELETE employeeId '" + employeeId + " was deleted successfully");
            }else{
                logger.info("==== DELETE [error] employeeId '" + employeeId + "' was not found in the record");
            }
        }catch (Exception e) {
            logger.info("==== DELETE employeeId '" + employeeId + "' was not in the record");
            throw new Exception("Employee updated failed : " + e);
        }
    }

    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId")Long employeeId) throws Exception {
        logger.info("==== PUT /employees/" + employeeId);

        try {
            if(employeeService.hasEmployeeId(employeeId) && employeeId.equals(employee.getId())){
                employeeService.updateEmployee(employee);
                cache.put(employeeId, employee);

                // set cache as null
                cache.put("all", null);
                logger.info("==== PUT employeeId '" + employeeId + "was updated to " + employee);
            }else{
                logger.info("==== PUT [error] employeeId was specified as '" + employeeId + "' but '"  + employee.getId() + "' was given");
            }
        }catch (Exception e) {
            logger.info("==== PUT employeeId '" + employeeId + "' was not in the record");
            throw new Exception("Employee updated failed : " + e);
        }
    }

}