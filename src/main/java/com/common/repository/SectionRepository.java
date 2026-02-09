package com.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.common.entity.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {
	
	Optional<Section> findBySectionName(String sectionName);
}
