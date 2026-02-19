package com.common.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sce_emp", schema = "sce_employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sce_employee.sce_emp_employee_id_seq")
    @SequenceGenerator(name = "sce_employee.sce_emp_employee_id_seq", sequenceName = "sce_employee.sce_emp_employee_id_seq", allocationSize = 1)
    @Column(name = "emp_id")
    private Integer empId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_join")
    private LocalDate dateOfJoin;

    @Column(name = "primary_mobile_no")
    private Long primaryMobileNo;

    @Column(name = "secondary_mobile_no")
    private Long secondaryMobileNo;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    // Relationships with existing entities

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "cmps_id")
    private Campus campus;

    @ManyToOne
    @JoinColumn(name = "agreement_org_id")
    private Organization agreementOrganization;

    // Self-referencing relationships

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_emp_id")
    @ToString.Exclude
    private Employee referenceEmp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hired_by_emp_id")
    @ToString.Exclude
    private Employee hiredByEmp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    @ToString.Exclude
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_manager_id")
    @ToString.Exclude
    private Employee reportingManager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replaced_by_emp_id")
    @ToString.Exclude
    private Employee replacedByEmp;

    // IDs for entities that are currently missing in the package
    // Note: Create these entities (Designation, Department, etc.) to map them as
    // objects.

    @ManyToOne
    @JoinColumn(name = "designation_id")
    private EmpDesignation designation;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private EmpDepartment department;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "emp_status_id")
    private Integer empStatusId;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "emp_type_id")
    private Integer empTypeId;

    @Column(name = "highest_qualification_id")
    private Integer highestQualificationId;

    @Column(name = "emp_work_mode_id")
    private Integer empWorkModeId;

    @Column(name = "contract_start_date")
    private LocalDate contractStartDate;

    @Column(name = "contract_end_date")
    private LocalDate contractEndDate;

    @Column(name = "join_type_id")
    private Integer joinTypeId;

    @Column(name = "mode_of_hiring_id")
    private Integer modeOfHiringId;

    @Column(name = "emp_app_status_id")
    private Integer empAppStatusId;

    @Column(name = "payroll_id")
    private String payrollId;

    @Column(name = "temp_payroll_id")
    private String tempPayrollId;

    @Column(name = "total_experience")
    private Double totalExperience;

    @Column(name = "emp_app_check_list_detl_id")
    private String empAppCheckListDetlId;

    @Column(name = "is_check_submit")
    private Integer isCheckSubmit;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "notice_period")
    private String noticePeriod;

    @Column(name = "aagreement_type")
    private String aagreementType;

    @Column(name = "pre_chaitanya_id")
    private String preChaitanyaId;

    @Column(name = "building_id")
    private Integer buildingId;

    @Column(name = "age")
    private Integer age;

    @Column(name = "ssc_no")
    private Long sscNo;
}