package com.common.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.common.dto.CampusLocationDTO;
import com.common.dto.EmployeeDetailsDTO;
import com.common.dto.EmployeePayrollDto;
import com.common.dto.GenericDropdownDTO;
import com.common.dto.PinCodeLocationDTO;

import com.common.entity.AcademicYear;
import com.common.entity.Campus;
import com.common.entity.CampusEmployee;
import com.common.entity.City;
import com.common.repository.AcademicYearRepository;
import com.common.repository.BloodGroupRepository;
import com.common.repository.CampusEmployeeRepository;
import com.common.repository.CampusOrganizationRepository;
import com.common.repository.CampusRepository;
import com.common.repository.CasteRepository;
import com.common.repository.CityRepository;
import com.common.repository.DistrictRepository;
import com.common.repository.EmpDepartmentRepository;
import com.common.repository.EmpDesignationRepository;
import com.common.repository.EmployeeRepository;
import com.common.repository.EmployeeViewRepository;
import com.common.repository.GenderRepository;
import com.common.repository.MandalRepository;
import com.common.repository.PaymentModeRepository;
import com.common.repository.PinCodeRepository;
import com.common.repository.ReligionRepository;
import com.common.repository.StateRepository;
import com.common.repository.StudentClassRepository;
import com.common.repository.ZoneRepository;

@Service
public class CommonServiceMethods {

    @Autowired
    ReligionRepository religionRepo;
    // @Autowired
    // OrganizationRepository organizationRepo;
    @Autowired
    GenderRepository genderRepo;
    @Autowired
    CasteRepository casteRepo;
    @Autowired
    EmployeeRepository employeeRepo;
    @Autowired
    StudentClassRepository classRepo;
    @Autowired
    PaymentModeRepository paymentModeRepo;
    @Autowired
    BloodGroupRepository bloodGroupRepo;
    @Autowired
    ZoneRepository zoneRepo;
    @Autowired
    StateRepository stateRepo;
    @Autowired
    DistrictRepository districtRepo;
    @Autowired
    CityRepository cityRepo;
    @Autowired
    AcademicYearRepository academicYearRepo;
    @Autowired
    MandalRepository mandalRepo;
    @Autowired
    PinCodeRepository pinCodeRepo;
    @Autowired
    CampusOrganizationRepository cmpsOrgRepo;
    @Autowired
    EmployeeViewRepository employeeViewRepo;
    @Autowired
    CampusEmployeeRepository campusEmployeeRepo;
    @Autowired
    CampusRepository campusRepo;
    @Autowired
    EmpDepartmentRepository empDeptRepo;
    @Autowired
    EmpDesignationRepository empDesigRepo;

    final int ACTIVE_STATUS = 1;

    @Cacheable(value = "religions")
    public List<GenericDropdownDTO> getAllReligions() {
        return religionRepo.findAll().stream()
                .map(r -> new GenericDropdownDTO(r.getReligion_id(), r.getReligion_type()))
                .collect(Collectors.toList());
    }

    //
    // public List<GenericDropdownDTO> getAllOrganizations() {
    // return organizationRepo.findAll().stream().map(org -> new
    // GenericDropdownDTO(org.getOrgId(), org.getOrg_name()))
    // .collect(Collectors.toList());
    // }
    @Cacheable(value = "genders")
    public List<GenericDropdownDTO> getAllGenders() {
        return genderRepo.findAll().stream().map(g -> new GenericDropdownDTO(g.getGender_id(), g.getGenderName()))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "castes")
    public List<GenericDropdownDTO> getAllCastes() {
        return casteRepo.findAll().stream().map(c -> new GenericDropdownDTO(c.getCaste_id(), c.getCaste_type()))
                .collect(Collectors.toList());
    }

    // public List<GenericDropdownDTO> getAllEmployees() {
    // List<Employee> activeEmployees = employeeRepo.findByIsActive(1);
    //
    // return activeEmployees.stream().map(employee -> new
    // GenericDropdownDTO(employee.getEmp_id(),
    // employee.getFirst_name() + " " +
    // employee.getLast_name())).collect(Collectors.toList());
    // }
    @Cacheable(value = "studentClasses")
    public List<GenericDropdownDTO> getAllStudentclass() {
        return classRepo.findAll().stream()
                .map(studentClass -> new GenericDropdownDTO(studentClass.getClassId(), studentClass.getClassName()))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "AllPaymentModes")
    public List<GenericDropdownDTO> getAllPaymentModes() {
        return paymentModeRepo.findAll().stream()
                .map(mode -> new GenericDropdownDTO(mode.getPayment_mode_id(), mode.getPayment_type()))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "BloodGroupTypes")
    public List<GenericDropdownDTO> getAllBloodGroups() {
        return bloodGroupRepo.findAll().stream()
                .map(group -> new GenericDropdownDTO(group.getBloodGroupId(), group.getBloodGroupName()))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "allZones")
    public List<GenericDropdownDTO> getAllZones() {
        return zoneRepo.findAllActiveZonesForDropdown();
    }

    @Cacheable(value = "allstates")
    public List<GenericDropdownDTO> getAllStates() {
        return stateRepo.findByStatus(1).stream().map(s -> new GenericDropdownDTO(s.getStateId(), s.getStateName()))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "academicYears")
    public List<AcademicYear> getAllAcademicYears() {
        return academicYearRepo.findAll();
    }

    @Cacheable(value = "AllMandals")
    public List<GenericDropdownDTO> getAllMandals() {
        return mandalRepo.findAll().stream().map(g -> new GenericDropdownDTO(g.getMandal_id(), g.getMandal_name()))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "districts")
    public List<GenericDropdownDTO> getAllDistricts() {
        return districtRepo.findByStatus(1).stream()
                .map(d -> new GenericDropdownDTO(d.getDistrictId(), d.getDistrictName())).collect(Collectors.toList());
    }

    @Cacheable(value = "cities")
    public List<GenericDropdownDTO> getAllCities() {
        return cityRepo.findByStatus(ACTIVE_STATUS).stream()
                .map(city -> new GenericDropdownDTO(city.getCityId(), city.getCityName())).collect(Collectors.toList());
    }

    @Cacheable(value = "pinCode", key = "#pinCode")
    public PinCodeLocationDTO getLocationByPinCode(int pinCode) {
        return pinCodeRepo.findStateAndDistrictByPinCode(pinCode)
                .orElseThrow(() -> new RuntimeException("No data found for pin code: " + pinCode));
    }

    public List<City> getCitiesByState(int stateId) {
        final int ACTIVE_STATUS = 1;
        return cityRepo.findByDistrictStateStateIdAndStatus(stateId, ACTIVE_STATUS);
    }

    @Cacheable(value = "organizationByCampus", key = "#campusId")
    public List<GenericDropdownDTO> getOrganizationByCampus(int campusId) {
        return cmpsOrgRepo.findByCampusCampusIdAndIsActive(campusId, 1).stream()
                .map(cmpsOrg -> new GenericDropdownDTO(cmpsOrg.getOrganization().getOrganizationId(),
                        cmpsOrg.getOrganization().getOrganizationName()))
                .collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getCitiesByDistrict(int districtId) {
        return cityRepo.findByDistrictDistrictIdAndStatus(districtId, 1).stream().map(
                city -> new GenericDropdownDTO(city.getCityId(), city.getCityName()))
                .collect(Collectors.toList());
    }

    public List<EmployeePayrollDto> getEmployeesByCampus(int campusId) {
        return employeeRepo.findByCampusCampusId(campusId).stream()
                .map(emp -> new EmployeePayrollDto(
                        emp.getEmpId(),
                        emp.getFirstName() + " " + emp.getLastName(),
                        emp.getPayrollId()))
                .collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getCampusesByEmployee(int empId, String category, Integer cityId) {
        Set<GenericDropdownDTO> campusSet = new HashSet<>();

        // Normalize category to null if blank
        String busTypeName = (category != null && !category.isBlank()) ? category : null;

        // 1. If user has an Admin role, filter all campuses by businessType (category)
        // and cityId
        boolean isAdmin = employeeViewRepo.findAllByEmpId(empId).stream()
                .anyMatch(ev -> ev.getRoleName() != null && ev.getRoleName().toLowerCase().contains("admin"));
        if (isAdmin) {
            List<com.common.entity.Campus> adminCampuses = campusRepo.findAdminCampuses(busTypeName, cityId, 1);
            for (com.common.entity.Campus c : adminCampuses) {
                campusSet.add(new GenericDropdownDTO(c.getCampusId(), c.getCampusName()));
            }
        }

        // 2 & 3. Collect linked campus IDs from Employee and CampusEmployee
        List<Integer> linkedCampusIds = new ArrayList<>();

        employeeRepo.findById(empId).ifPresent(emp -> {
            if (emp.getCampus() != null && emp.getCampus().getCampusId() != null) {
                linkedCampusIds.add(emp.getCampus().getCampusId());
            }
        });

        List<CampusEmployee> campusRoles = campusEmployeeRepo.findByEmployeeEmpIdAndIsActive(empId, 1);
        for (CampusEmployee ce : campusRoles) {
            if (ce.getCampus() != null && ce.getCampus().getCampusId() != null) {
                linkedCampusIds.add(ce.getCampus().getCampusId());
            }
        }

        // Query the linked campuses, filtered by businessType (category) and cityId if
        // provided
        if (!linkedCampusIds.isEmpty()) {
            List<com.common.entity.Campus> linkedCampuses = campusRepo.findLinkedCampuses(linkedCampusIds, busTypeName,
                    cityId);
            for (com.common.entity.Campus c : linkedCampuses) {
                campusSet.add(new GenericDropdownDTO(c.getCampusId(), c.getCampusName()));
            }
        }

        return new ArrayList<>(campusSet);
    }

    public List<GenericDropdownDTO> getCampusesByZone(int zoneId) {
        return campusRepo.findByZoneZoneIdAndIsActive(zoneId, ACTIVE_STATUS).stream()
                .map(campus -> new GenericDropdownDTO(campus.getCampusId(), campus.getCampusName()))
                .collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getZonesByCity(int cityId) {
        return zoneRepo.findByCityCityId(cityId).stream()
                .map(zone -> new GenericDropdownDTO(zone.getZoneId(), zone.getZoneName()))
                .collect(Collectors.toList());
    }

    public Long getEmployeePhoneNumber(String identifier) {
        com.common.entity.Employee emp = null;

        if (identifier != null && !identifier.isBlank()) {
            try {
                Integer empId = Integer.parseInt(identifier);
                emp = employeeRepo.findById(empId).orElse(null);
            } catch (NumberFormatException e) {
                // Not an integer, skip empId check
            }

            if (emp == null) {
                emp = employeeRepo.findByPayrollId(identifier).orElse(null);
            }
        }

        if (emp != null) {
            if (emp.getPrimaryMobileNo() != null) {
                return emp.getPrimaryMobileNo();
            }
            return emp.getSecondaryMobileNo();
        }
        return null;
    }

    @Cacheable(value = "campusByOrganization", key = "#orgId")
    public List<GenericDropdownDTO> getCampusByOrganization(Integer orgId) {
        return cmpsOrgRepo.findByOrganizationOrganizationIdAndIsActive(orgId, 1).stream()
                .map(cmpsOrg -> new GenericDropdownDTO(cmpsOrg.getCampus().getCampusId(),
                        cmpsOrg.getCampus().getCampusName()))
                .collect(Collectors.toList());
    }

    public CampusLocationDTO getLocationByCampusId(int campusId) {
        Campus campus = campusRepo.findById(campusId)
                .orElseThrow(() -> new RuntimeException("Campus not found with id: " + campusId));

        CampusLocationDTO dto = new CampusLocationDTO();
        if (campus.getZone() != null) {
            dto.setZoneId(campus.getZone().getZoneId());
            dto.setZoneName(campus.getZone().getZoneName());

            if (campus.getZone().getCity() != null) {
                dto.setCityId(campus.getZone().getCity().getCityId());
                dto.setCityName(campus.getZone().getCity().getCityName());
            }
        }
        return dto;
    }

    public List<EmployeeDetailsDTO> getEmployeesByDeptAndDesig(Integer departmentId, Integer designationId) {
        List<com.common.entity.Employee> employees;
        if (designationId != null) {
            employees = employeeRepo.findByDepartmentDepartmentIdAndDesignationDesignationIdAndIsActive(departmentId,
                    designationId, 1);
        } else {
            employees = employeeRepo.findByDepartmentDepartmentIdAndIsActive(departmentId, 1);
        }

        return employees.stream().map(emp -> new EmployeeDetailsDTO(
                emp.getEmpId(),
                emp.getFirstName() + " " + emp.getLastName(),
                emp.getPayrollId(),
                emp.getDesignation() != null ? emp.getDesignation().getDesignationId() : null,
                emp.getDesignation() != null ? emp.getDesignation().getDesignationName() : null,
                emp.getPrimaryMobileNo(),
                emp.getEmail())).collect(Collectors.toList());
    }

    @Cacheable(value = "allDepartments")
    public List<GenericDropdownDTO> getAllDepartments() {
        return empDeptRepo.findByIsActive(ACTIVE_STATUS).stream()
                .map(dept -> new GenericDropdownDTO(dept.getDepartmentId(), dept.getDepartmentName()))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "designationsByDept", key = "#departmentId")
    public List<GenericDropdownDTO> getDesignationsByDepartment(Integer departmentId) {
        return empDesigRepo.findByDepartmentDepartmentIdAndIsActive(departmentId, ACTIVE_STATUS).stream()
                .map(desig -> new GenericDropdownDTO(desig.getDesignationId(), desig.getDesignationName()))
                .collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getCitiesByEmployee(int empId, String category, Integer stateId) {

        String busTypeName = (category != null && !category.isBlank()) ? category : null;

        // Optimized admin check
        if (employeeViewRepo.existsByEmpIdAndAdminRole(empId)) {
            List<com.common.entity.City> adminCities;
            if (stateId != null) {
                adminCities = cityRepo.findByDistrictStateStateIdAndStatus(stateId, ACTIVE_STATUS);
            } else {
                adminCities = cityRepo.findByStatus(ACTIVE_STATUS);
            }
            return adminCities.stream()
                    .map(city -> new GenericDropdownDTO(city.getCityId(), city.getCityName()))
                    .collect(Collectors.toList());
        }

        // Optimized campus IDs retrieval: collect both primary and assigned campuses
        Set<Integer> campusIds = new HashSet<>();
        Integer primaryCampusId = employeeRepo.findCampusIdByEmpId(empId);
        if (primaryCampusId != null) {
            campusIds.add(primaryCampusId);
        }
        campusIds.addAll(campusEmployeeRepo.findCampusIdsByEmpId(empId));

        if (campusIds.isEmpty()) {
            return new ArrayList<>();
        }

        // Optimized city DTO retrieval: single query with projection
        return campusRepo.findUniqueCitiesByCampusIds(new ArrayList<>(campusIds), busTypeName, stateId);
    }
}