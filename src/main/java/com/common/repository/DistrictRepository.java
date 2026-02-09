package com.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.common.dto.GenericDropdownDTO;
import com.common.entity.District;

@Repository
public interface DistrictRepository  extends JpaRepository<District, Integer>{
	
	List<District> findByStateStateId(int stateId);
	List<District> findByStatus(int status);
}
