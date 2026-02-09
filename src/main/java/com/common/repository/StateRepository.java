package com.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.common.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer>{
	
	List<State> findByStatus(int status);
}
