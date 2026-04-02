package com.common.repository;
 
import java.util.List;
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.common.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByIsActive(int isActive);

    List<Employee> findByCampusCampusId(int campusId);

    Optional<Employee> findByPayrollId(String payrollId);

    List<Employee> findByDepartmentDepartmentIdAndDesignationDesignationIdAndIsActive(Integer departmentId, Integer designationId, Integer isActive);

    List<Employee> findByDepartmentDepartmentIdAndIsActive(Integer departmentId, Integer isActive);

}
