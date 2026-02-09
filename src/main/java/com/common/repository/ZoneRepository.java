package com.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.common.dto.GenericDropdownDTO;
import com.common.entity.Zone;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Integer>{

    List<Zone> findByCityCityId(int cityId);
    Optional<Zone> findByZoneNameIgnoreCase(String zoneName);
    
    @Query("SELECT NEW com.common.dto.GenericDropdownDTO(z.zoneId, z.zoneName) " +
	           "FROM Zone z ")
	    List<GenericDropdownDTO> findAllActiveZonesForDropdown();

}