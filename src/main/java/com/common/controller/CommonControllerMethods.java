package com.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.dto.GenericDropdownDTO;
import com.common.dto.PinCodeLocationDTO;
import com.common.entity.AcademicYear;
import com.common.entity.City;
import com.common.service.CommonServiceMethods;

@RestController
@RequestMapping("/common/get")
@CrossOrigin("*")
public class CommonControllerMethods {

	@Autowired
	CommonServiceMethods commonServiceMethods;

	@GetMapping("/religions")
	public List<GenericDropdownDTO> getReligions() {
		return commonServiceMethods.getAllReligions();
	}

//	@GetMapping("/organizations")
//	public List<GenericDropdownDTO> getAllOrganizations() {
//		return commonServiceMethods.getAllOrganizations();
//	}

	@GetMapping("/genders")
	public List<GenericDropdownDTO> getGenders() {
		return commonServiceMethods.getAllGenders();
	}

	@GetMapping("/castes")
	public List<GenericDropdownDTO> getCastes() {
		return commonServiceMethods.getAllCastes();
	}

//	@GetMapping("/all_employees")
//	public List<GenericDropdownDTO> getAllEmployees() {
//		return commonServiceMethods.getAllEmployees();
//	}

	@GetMapping("/all/Studentclass")
	public List<GenericDropdownDTO> getAllStudentClass() {
		return commonServiceMethods.getAllStudentclass();
	}

	@GetMapping("/PaymentModes/all")
	public List<GenericDropdownDTO> getAllPaymentModes() {
		return commonServiceMethods.getAllPaymentModes();
	}

	@GetMapping("/BloodGroup/all")
	public List<GenericDropdownDTO> getAllBloodGroups() {
		return commonServiceMethods.getAllBloodGroups();
	}

	@GetMapping("/zones")
	public ResponseEntity<List<GenericDropdownDTO>> getAllZones() {
		List<GenericDropdownDTO> zone = commonServiceMethods.getAllZones();
		return new ResponseEntity<>(zone, HttpStatus.OK);
	}

	@GetMapping("/states")
	public ResponseEntity<List<GenericDropdownDTO>> getStates() {
		return ResponseEntity.ok(commonServiceMethods.getAllStates());
	}

	@GetMapping("/getalldistricts")
	public List<GenericDropdownDTO> getAllDistricts() {
		return commonServiceMethods.getAllDistricts();
	}

	@GetMapping("/cities")
	public List<GenericDropdownDTO> getCities() {
		return commonServiceMethods.getAllCities();
	}

	@GetMapping("/academic-years")
	public ResponseEntity<List<AcademicYear>> getAcademicYears() {
		return ResponseEntity.ok(commonServiceMethods.getAllAcademicYears());
	}

	@GetMapping("/mandals")
	public List<GenericDropdownDTO> getAllMandals() {
		return commonServiceMethods.getAllMandals();
	}

	@GetMapping("/{pinCode}")
	public PinCodeLocationDTO getLocationByPinCode(@PathVariable int pinCode) {
		return commonServiceMethods.getLocationByPinCode(pinCode);
	}
	@GetMapping("/city/{stateId}")//used/c
	public ResponseEntity<List<City>> getCitiesByState(@PathVariable int stateId) {
		return ResponseEntity.ok(commonServiceMethods.getCitiesByState(stateId));
	}
}
