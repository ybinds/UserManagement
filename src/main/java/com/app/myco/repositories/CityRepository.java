package com.app.myco.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.myco.entities.City;

public interface CityRepository extends JpaRepository<City, Serializable>{

}
