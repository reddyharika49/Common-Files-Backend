package com.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.common.entity.EmpDepartment;

@Repository
public interface EmpDepartmentRepository extends JpaRepository<EmpDepartment, Integer> {
}