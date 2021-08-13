package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.dto.Department;
import jp.co.axa.apidemo.entities.Employee;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    //List<Department> findDepartmentCount();
}
