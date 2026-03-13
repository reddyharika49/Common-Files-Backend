package com.common.repository;
 
import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.common.entity.CampusOrganization;
 
@Repository
public interface CampusOrganizationRepository extends JpaRepository<CampusOrganization, Integer> {
 
	List<CampusOrganization> findByCampusCampusIdAndIsActive(Integer campusId, Integer isActive);
	
	List<CampusOrganization> findByOrganizationOrganizationIdAndIsActive(Integer organizationId, Integer isActive);
 
}
