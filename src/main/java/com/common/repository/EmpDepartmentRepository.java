package com.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.common.entity.EmpDepartment;

@Repository
public interface EmpDepartmentRepository extends JpaRepository<EmpDepartment, Integer> {
    List<EmpDepartment> findByIsActive(Integer isActive);
}