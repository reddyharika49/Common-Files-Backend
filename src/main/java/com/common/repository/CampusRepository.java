package com.common.repository;
 
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.common.dto.GenericDropdownDTO;
import com.common.entity.Campus;
 
@Repository
public interface CampusRepository extends JpaRepository<Campus, Integer> {
 
    // For admin: all active campuses filtered by businessType name (college/school) and optionally by cityId
    @org.springframework.data.jpa.repository.Query("SELECT c FROM Campus c WHERE c.isActive = :isActive " +
            "AND (:businessTypeName IS NULL OR LOWER(c.businessType.businessTypeName) = LOWER(:businessTypeName)) " +
            "AND (:cityId IS NULL OR c.city.cityId = :cityId)")
    List<Campus> findAdminCampuses(String businessTypeName, Integer cityId, Integer isActive);
 
    // For non-admin: linked campus IDs filtered by businessType name, optionally by cityId, and MUST be active
    @org.springframework.data.jpa.repository.Query("SELECT c FROM Campus c WHERE c.campusId IN :campusIds " +
            "AND c.isActive = 1 " +
            "AND (:businessTypeName IS NULL OR LOWER(c.businessType.businessTypeName) = LOWER(:businessTypeName)) " +
            "AND (:cityId IS NULL OR c.city.cityId = :cityId)")
    List<Campus> findLinkedCampuses(java.util.List<Integer> campusIds, String businessTypeName, Integer cityId);

    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT new com.common.dto.GenericDropdownDTO(c.city.cityId, c.city.cityName) " +
            "FROM Campus c WHERE c.campusId IN :campusIds " +
            "AND c.isActive = 1 " +
            "AND (:businessTypeName IS NULL OR LOWER(c.businessType.businessTypeName) = LOWER(:businessTypeName)) " +
            "AND (:stateId IS NULL OR c.state.stateId = :stateId) " +
            "AND c.city IS NOT NULL")
    List<GenericDropdownDTO> findUniqueCitiesByCampusIds(List<Integer> campusIds, String businessTypeName, Integer stateId);
 
    List<Campus> findByBusinessTypeBusinessTypeNameIgnoreCaseAndIsActive(String businessTypeName, Integer isActive);
 
    // For admin: all active campuses (no filter)
    List<Campus> findByIsActive(Integer isActive);
 
    // For non-admin: linked campus IDs filtered by businessType name
    List<Campus> findByCampusIdInAndBusinessTypeBusinessTypeNameIgnoreCase(List<Integer> campusIds,
            String businessTypeName);
 
    // For non-admin: all linked campus IDs (no filter)
    List<Campus> findByCampusIdIn(List<Integer> campusIds);
 
            List<Campus> findByZoneZoneIdAndIsActive(int zoneId, int isActive);
}