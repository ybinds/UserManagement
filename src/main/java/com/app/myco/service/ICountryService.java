package com.app.myco.service;

import java.util.Map;

public interface ICountryService {

	Map<Integer, String> getAllCountries();
	Map<Integer, String> getStatesByCountry(Integer id);
}
