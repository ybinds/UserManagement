package com.app.myco.service;

import java.util.List;

public interface ICountryService {

	List<Object[]> getAllCountries();
	List<Object[]> getStatesByCountry(Integer id);
}
