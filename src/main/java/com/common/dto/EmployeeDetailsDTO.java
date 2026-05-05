package com.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailsDTO {
    private Integer empId;
    private String name;
    private String payrollId;
    private Integer designationId;
    private String designationName;
    private Long mobileNo;
    private String mail;
    private Integer campusId;
    private String campusName;
}
