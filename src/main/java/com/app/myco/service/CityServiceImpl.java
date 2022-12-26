package com.app.myco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.myco.entities.City;
import com.app.myco.repositories.CityRepository;

@Service	
public class CityServiceImpl implements ICityService {

	@Autowired
	private CityRepository repo;
	
	public List<City> getAllCities() {
		return repo.findAll();
	}

}
