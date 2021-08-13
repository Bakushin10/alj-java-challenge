package jp.co.axa.apidemo.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * retrieve all employee records from db
     */
    public List<Employee> retrieveEmployees() {
        try {
            logger.info("----->>> retrieveEmployees");
            List<Employee> employees = employeeRepository.findAll();
            return employees;
        } catch (Exception e) {
            logger.error("retrieveEmployees exception: " + e.getMessage());
            throw e;
        }
    }

    /**
     * getEmployee all employee records from db
     */
    public Employee getEmployee(Long employeeId) {
        try {
            logger.info("----->>> getEmployee");
            Optional<Employee> optEmp = employeeRepository.findById(employeeId);
            return optEmp.get();
        } catch (Exception e) {
            logger.error("getEmployee exception: " + e.getMessage());
            throw e;
        }
    }

    public void saveEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}