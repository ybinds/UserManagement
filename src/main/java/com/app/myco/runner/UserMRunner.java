package com.app.myco.runner;

import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.app.myco.entities.City;
import com.app.myco.entities.Country;
import com.app.myco.entities.State;
import com.app.myco.repositories.CityRepository;
import com.app.myco.repositories.CountryRepository;
import com.app.myco.repositories.StateRepository;

@Component
public class UserMRunner implements CommandLineRunner{

	@Autowired
	private CountryRepository crepo;
	@Autowired
	private StateRepository srepo;
	@Autowired
	private CityRepository cityRepo;
	
	public void run(String... args) throws Exception {
		City c1 = new City(1,"Hyderabad");
		City c2 = new City(2, "Mumbai");
		City c3 = new City(3, "Bangalore");
		City c4 = new City(4, "Los Angeles");
		City c5 = new City(5, "Atlanta");
		City c6 = new City(6, "Dallas");
		cityRepo.saveAll(
				Arrays.asList( c1, c2, c3, c4, c5, c6 ));
		State s1 = new State(1,"Telangana", Set.of(c1));
		State s2 = new State(2, "Maharashtra", Set.of(c2));
		State s3 = new State(3, "Karnataka", Set.of(c3));
		State s4 = new State(4, "California", Set.of(c4));
		State s5 = new State(5, "Georgia", Set.of(c5));
		State s6 = new State(6, "Texas", Set.of(c6));
		srepo.saveAll(
				Arrays.asList(s1,s2,s3,s4,s5,s6));
		
		Country cn1 = new Country(1,"India", Set.of(s1,s2,s3));
		Country cn2 = new Country(2,"USA", Set.of(s4,s5,s6));
		crepo.saveAll(Arrays.asList(cn1, cn2));
	}

}
