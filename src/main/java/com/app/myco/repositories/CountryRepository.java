package com.app.myco.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.myco.entities.Country;

public interface CountryRepository extends JpaRepository<Country, Serializable> {

	@Query("SELECT c.countryId, c.countryName FROM Country c")
	List<Object[]> getAllCountries();
	
	@Query("SELECT s.stateId, s.stateName FROM Country c INNER JOIN c.states s WHERE c.id=:id")
	List<Object[]> getStatesByCountry(Integer id);
}
