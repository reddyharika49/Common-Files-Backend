package com.common.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmps_id")
    private Integer campusId;

    @Column(name = "cmps_name")
    private String campusName;

    @Column(name = "cmps_code")
    private String cmpsCode;

    @Column(name = "cmps_type")
    private String cmpsType;

    @Column(name = "code")
    private Integer code;

    // ── City ──────────────────────────────────────────────────────────────────
    @Column(name = "city_id", insertable = false, updatable = false)
    private Integer cityId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    // ── State ─────────────────────────────────────────────────────────────────
    @Column(name = "state_id", insertable = false, updatable = false)
    private Integer stateId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    // ── Zone ──────────────────────────────────────────────────────────────────
    @Column(name = "zone_id", insertable = false, updatable = false)
    private Integer zoneId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private BusinessType businessType;

    // ── Status & Flags ────────────────────────────────────────────────────────
    @Column(name = "cmps_status")
    private Integer cmpsStatus;

    @Column(name = "is_active")
    private Integer isActive;

    // ── Fusion Fields ─────────────────────────────────────────────────────────
    @Column(name = "fusion_cmps_code")
    private String fusionCmpsCode;

    @Column(name = "fusion_cmps_name")
    private String fusionCmpsName;

    // ── Additional Info ───────────────────────────────────────────────────────
    @Column(name = "orc_code")
    private String orcCode;

    @Column(name = "year_of_established")
    private Integer yearOfEstablished;

    @Column(name = "is_ground_available")
    private Integer isGroundAvailable;

    @Column(name = "is_farms_available")
    private Integer isFarmsAvailable;

    @Column(name = "is_zone_cmps")
    private String isZoneCmps;

    // ── Audit Fields ──────────────────────────────────────────────────────────
    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}