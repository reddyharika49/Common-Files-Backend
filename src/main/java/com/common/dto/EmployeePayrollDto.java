package com.common.dto;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePayrollDto {
 
    private Integer empId;
    private String employeeName;
    private String payrollId;
}
 
