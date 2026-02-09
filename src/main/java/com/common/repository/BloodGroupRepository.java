package com.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.common.entity.BloodGroup;

@Repository
public interface BloodGroupRepository extends JpaRepository<BloodGroup, Integer>{

}
