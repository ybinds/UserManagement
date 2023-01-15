package com.app.myco.rest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(CountryRestController.class);
	
	@GetMapping("/list")
	public ResponseEntity<Map<Integer, String>> getAllCountries(){
		return ResponseEntity.ok(service.getAllCountries());
	}
	
	@GetMapping("/states/{id}")
	public ResponseEntity<Map<Integer, String>> getStatesByCountry(
			@PathVariable("id") Integer id){
		logger.debug("entering states list endpoint");
		ResponseEntity<Map<Integer, String>> response = null;
		try {
			Map<Integer,String> map = service.getStatesByCountry(id);
			if(map.size()==0) {
				logger.warn("Received 0 results from states list by country " + id);
			}
			response = ResponseEntity.ok(map);
		} catch(CountryNotFoundException cnfe) {
			logger.error("Error occurred in states list endpoint "+ cnfe.getMessage());
			cnfe.printStackTrace();
			throw cnfe;
		}
		logger.debug("exiting states list endpoint");
		return response;
	}
}
