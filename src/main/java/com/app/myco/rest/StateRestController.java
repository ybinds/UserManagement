package com.app.myco.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.myco.exception.StateNotFoundException;
import com.app.myco.service.IStateService;

@RestController
@RequestMapping("/state")
public class StateRestController {

	@Autowired
	private IStateService service;
	
	@GetMapping("/cities/{id}")
	public ResponseEntity<List<Object[]>> getCitiesByState(
			@PathVariable("id") Integer id){
		ResponseEntity<List<Object[]>> response = null;
		try {
			response = ResponseEntity.ok(service.getCitiesByState(id));
		} catch(StateNotFoundException snfe) {
			snfe.printStackTrace();
			throw snfe;
		}
		return response;
	}
}
