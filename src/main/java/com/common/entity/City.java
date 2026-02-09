package com.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sce_city", schema = "sce_locations")
public class City {
    
    @Id
    @Column(name = "city_id")
    private int cityId;
    
    @Column(name = "city_name")
    private String cityName;
    @Column(name="payroll_city_code")

    private String payroll_city_code;


    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;
    
    private int status;
}
