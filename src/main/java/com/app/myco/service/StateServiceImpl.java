package com.app.myco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.myco.exception.StateNotFoundException;
import com.app.myco.repositories.StateRepository;

@Service
public class StateServiceImpl implements IStateService {

	@Autowired
	private StateRepository repo;
	
	public List<Object[]> getCitiesByState(Integer id) {
		if(id==null || !repo.existsById(id))
			throw new StateNotFoundException("STATE WITH '"+ id + "' NOT FOUND");
		else
			return repo.getCitiesByState(id);
	}

}
