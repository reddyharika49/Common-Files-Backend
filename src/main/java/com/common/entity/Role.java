
package com.common.entity;
 
import java.time.LocalDateTime;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sce_role", schema = "sce_admin")
public class Role {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;
 
    @Column(name = "role_name", length = 50, nullable = false)
    private String roleName;
 
    @Column(name = "description", length = 100)
    private String description;
 
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
 
 
