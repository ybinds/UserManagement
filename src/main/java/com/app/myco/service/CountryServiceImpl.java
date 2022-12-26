package com.app.myco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.myco.exception.CountryNotFoundException;
import com.app.myco.repositories.CountryRepository;

@Service
public class CountryServiceImpl implements ICountryService {

	@Autowired
	private CountryRepository repo;
	
	public List<Object[]> getAllCountries() {
		return repo.getAllCountries();
	}

	public List<Object[]> getStatesByCountry(Integer id) {
		if(id==null || !repo.existsById(id))
			throw new CountryNotFoundException("COUNTRY WITH '"+ id + "' NOT FOUND");
		else
			return repo.getStatesByCountry(id);
	}
}
