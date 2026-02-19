package com.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.common.entity.EmpDesignation;

@Repository
public interface EmpDesignationRepository extends JpaRepository<EmpDesignation, Integer> {
}