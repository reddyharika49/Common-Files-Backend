package com.common.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.common.dto.GenericDropdownDTO;
import com.common.dto.PinCodeLocationDTO;
import com.common.entity.AcademicYear;
import com.common.entity.City;
import com.common.repository.AcademicYearRepository;
import com.common.repository.BloodGroupRepository;
import com.common.repository.CasteRepository;
import com.common.repository.CityRepository;
import com.common.repository.DistrictRepository;
//import com.common.repository.EmployeeRepository;
import com.common.repository.GenderRepository;
import com.common.repository.MandalRepository;
//import com.common.repository.OrganizationRepository;
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
//	@Autowired
//	OrganizationRepository organizationRepo;
	@Autowired
	GenderRepository genderRepo;
	@Autowired
	CasteRepository casteRepo;
//	@Autowired
//	EmployeeRepository employeeRepo;
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

	final int ACTIVE_STATUS = 1;
    @Cacheable(value = "religions")
	public List<GenericDropdownDTO> getAllReligions() {
		return religionRepo.findAll().stream()
				.map(r -> new GenericDropdownDTO(r.getReligion_id(), r.getReligion_type()))
				.collect(Collectors.toList());
	}
//
//	public List<GenericDropdownDTO> getAllOrganizations() {
//		return organizationRepo.findAll().stream().map(org -> new GenericDropdownDTO(org.getOrgId(), org.getOrg_name()))
//				.collect(Collectors.toList());
//	}
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

//	public List<GenericDropdownDTO> getAllEmployees() {
//		List<Employee> activeEmployees = employeeRepo.findByIsActive(1);
//
//		return activeEmployees.stream().map(employee -> new GenericDropdownDTO(employee.getEmp_id(),
//				employee.getFirst_name() + " " + employee.getLast_name())).collect(Collectors.toList());
//	}
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
    @Cacheable(value="pinCode", key= "#pinCode")
	public PinCodeLocationDTO getLocationByPinCode(int pinCode) {
		return pinCodeRepo.findStateAndDistrictByPinCode(pinCode)
				.orElseThrow(() -> new RuntimeException("No data found for pin code: " + pinCode));
	}
	public List<City> getCitiesByState(int stateId) {
		final int ACTIVE_STATUS = 1;
		return cityRepo.findByDistrictStateStateIdAndStatus(stateId, ACTIVE_STATUS);
	}
}
