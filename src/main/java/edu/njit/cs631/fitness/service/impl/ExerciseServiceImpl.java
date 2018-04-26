package edu.njit.cs631.fitness.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.njit.cs631.fitness.data.entity.Exercise;
import edu.njit.cs631.fitness.data.repository.ExerciseRepository;
import edu.njit.cs631.fitness.service.api.ExerciseService;

@Service("exerciseService")
public class ExerciseServiceImpl implements ExerciseService {
	
	@Autowired
	private ExerciseRepository exerciseRepository;

	public ExerciseServiceImpl() {
		super();
	}

	@Override
	public List<Exercise> listAllExercises() {
		// TODO maybe add sort 
		return exerciseRepository.findAll();
	}

}
