package com.common.entity;
 
import java.time.LocalDateTime;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sce_cmps_emp", schema = "sce_campus")
public class CampusEmployee {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sce_campus.sce_cmps_employee_cmps_employee_id_seq")
    @SequenceGenerator(name = "sce_campus.sce_cmps_employee_cmps_employee_id_seq", sequenceName = "sce_campus.sce_cmps_employee_cmps_employee_id_seq", allocationSize = 1)
    @Column(name = "cmps_employee_id")
    private Integer cmpsEmployeeId;
 
    @ManyToOne
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;
 
    @ManyToOne
    @JoinColumn(name = "cmps_id", nullable = false)
    private Campus campus;
 
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
 
    @Column(name = "attendance_status")
    private Integer attendanceStatus;
 
    @Column(name = "is_active", nullable = false)
    private Integer isActive = 1;
 
    @Column(name = "created_by", nullable = false)
    private Integer createdBy;
 
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
 
    @Column(name = "updated_by")
    private Integer updatedBy;
 
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}
 
 