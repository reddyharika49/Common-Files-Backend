package com.common.entity;
 
import com.fasterxml.jackson.annotation.JsonIgnore;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sce_cmps", schema = "sce_campus")
public class Campus {
 
    @Id
 
    @Column(name = "cmps_id")
    private Integer campusId;
 
    @Column(name = "cmps_name")
    private String campusName;
    private String cmps_type;
    private String cmps_code;
    private Integer code;
 
    @Column(name = "city_id", insertable = false, updatable = false)
    private Integer city_id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    // --- FIX: Use the correct foreign key column name ---
    @JoinColumn(name = "business_id") // Changed from business_type_id
    private BusinessType businessType;
 
    @Column(name = "is_active")
    private Integer isActive;
   
   
    @Column(name="cmps_status")
    private Integer cmpsStatus;
   
    @Column(name ="fusion_cmps_code")
    private String fusionCmpsCode;
   
    @Column(name ="fusion_cmps_name")
    private String fusionCmpsName;
   
    @Column(name = "orc_code")
    private String orcCode;
   
    @Column(name ="year_of_established")
    private Integer yearOfEstablished;
   
    @Column(name="is_ground_available")
    private Integer isGroundAvailable;
   
    @Column(name ="isFarmsAvailable")
    private Integer isFarmsAvailable;
}