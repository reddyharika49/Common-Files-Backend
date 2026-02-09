package com.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.common.entity.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer>{

	Optional<Gender> findByGenderName(String gender);
	List<Gender> findByIsActive(int isActive);

}
