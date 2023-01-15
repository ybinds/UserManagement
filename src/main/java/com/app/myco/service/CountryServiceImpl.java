package com.app.myco.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.myco.exception.CountryNotFoundException;
import com.app.myco.repositories.CountryRepository;

@Service
public class CountryServiceImpl implements ICountryService {

	@Autowired
	private CountryRepository repo;
	
	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);
	
	public Map<Integer, String> getAllCountries() {
		logger.info("getAllCountries() - service method start");
		Map<Integer, String> map = new HashMap<>();
		repo.getAllCountries().forEach(obj -> map.put((int) obj[0], obj[1].toString()));
		logger.info("getAllCountries() - service method end");
		return map;
	}

	public Map<Integer, String> getStatesByCountry(Integer id) {
		logger.info("getStatesByCountry() - service method start");
		Map<Integer, String> map = new HashMap<>();
		if(id==null || !repo.existsById(id))
			throw new CountryNotFoundException("COUNTRY WITH '"+ id + "' NOT FOUND");
		else {
			repo.getStatesByCountry(id).forEach(obj-> map.put(Integer.parseInt(obj[0].toString()), obj[1].toString()));
		}
		logger.info("getStatesByCountry() - service method end");
		return map;
	}
}
