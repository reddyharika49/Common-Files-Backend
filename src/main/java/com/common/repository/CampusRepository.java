package com.common.repository;
 
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.common.entity.Campus;
 
@Repository
public interface CampusRepository extends JpaRepository<Campus, Integer> {
 
    // For admin: all active campuses filtered by businessType name (college/school)
    List<Campus> findByBusinessTypeBusinessTypeNameIgnoreCaseAndIsActive(String businessTypeName, Integer isActive);
 
    // For admin: all active campuses (no filter)
    List<Campus> findByIsActive(Integer isActive);
 
    // For non-admin: linked campus IDs filtered by businessType name
    List<Campus> findByCampusIdInAndBusinessTypeBusinessTypeNameIgnoreCase(List<Integer> campusIds,
            String businessTypeName);
 
    // For non-admin: all linked campus IDs (no filter)
    List<Campus> findByCampusIdIn(List<Integer> campusIds);
}
 
