package com.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampusLocationDTO {
    private Integer zoneId;
    private String zoneName;
    private Integer cityId;
    private String cityName;
}
