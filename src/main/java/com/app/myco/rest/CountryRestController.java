package com.app.myco.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.myco.exception.CountryNotFoundException;
import com.app.myco.service.ICountryService;

@RestController
@CrossOrigin
@RequestMapping("/country")
public class CountryRestController {

	@Autowired
	private ICountryService service;
	
	@GetMapping("/list")
	public ResponseEntity<List<Object[]>> getAllCountries(){
		return ResponseEntity.ok(service.getAllCountries());
	}
	
	@GetMapping("/states/{id}")
	public ResponseEntity<List<Object[]>> getStatesByCountry(
			@PathVariable("id") Integer id){
		ResponseEntity<List<Object[]>> response = null;
		try {
			response = ResponseEntity.ok(service.getStatesByCountry(id));
		} catch(CountryNotFoundException cnfe) {
			cnfe.printStackTrace();
			throw cnfe;
		}
		return response;
	}
}
