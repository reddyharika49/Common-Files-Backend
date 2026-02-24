package com.common.entity;
 
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
 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sce_cmps_org", schema = "sce_campus")
public class CampusOrganization {
 
	@Id
	@Column(name = "cmps_org_id")
	private Integer campusOrganizationId;
 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cmps_id")
	private Campus campus;
 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "org_id")
	private Organization orginization;
 
	@Column(name = "is_active")
	private Integer isActive;
 
}
