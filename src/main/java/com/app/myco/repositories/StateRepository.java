package com.app.myco.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.myco.entities.State;

public interface StateRepository extends JpaRepository<State, Serializable> {

	@Query("SELECT c.cityId, c.cityName FROM State s INNER JOIN s.cities c WHERE s.id=:id")
	List<Object[]> getCitiesByState(Integer id);
}
