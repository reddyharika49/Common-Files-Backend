package com.common.repository;
 
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.common.entity.CampusEmployee;
 
@Repository
public interface CampusEmployeeRepository extends JpaRepository<CampusEmployee, Integer> {
    List<CampusEmployee> findByEmployeeEmpIdAndIsActive(int empId, int isActive);

    @org.springframework.data.jpa.repository.Query("SELECT ce.campus.campusId FROM CampusEmployee ce WHERE ce.employee.empId = :empId AND ce.isActive = 1")
    List<Integer> findCampusIdsByEmpId(@org.springframework.data.repository.query.Param("empId") int empId);
}
