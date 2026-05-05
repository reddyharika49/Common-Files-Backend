package com.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeMainCampusDTO {
    private Integer campusId;
    private String campusName;
    private Integer districtId;
    private String districtName;
    private Integer cityId;
    private String cityName;
    private Integer stateId;
    private String stateName;
}
