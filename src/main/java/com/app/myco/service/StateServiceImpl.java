package com.app.myco.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.myco.exception.StateNotFoundException;
import com.app.myco.repositories.StateRepository;

@Service
public class StateServiceImpl implements IStateService {

	@Autowired
	private StateRepository repo;
	
	public Map<Integer, String> getCitiesByState(Integer id) {
		Map<Integer, String> map = new HashMap<>();
		if(id==null || !repo.existsById(id))
			throw new StateNotFoundException("STATE WITH '"+ id + "' NOT FOUND");
		else
			repo.getCitiesByState(id).forEach(obj -> map.put(Integer.parseInt(obj[0].toString()), obj[1].toString()));
			return map;
	}

}
