package com.common.repository;
 
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.common.entity.CampusEmployee;
 
@Repository
public interface CampusEmployeeRepository extends JpaRepository<CampusEmployee, Integer> {
    List<CampusEmployee> findByEmployeeEmpIdAndIsActive(int empId, int isActive);
}
 
