package edu.njit.cs631.fitness.service.impl;

import java.util.List;

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.repository.ClazzRepository;
import edu.njit.cs631.fitness.service.api.ClazzService;
import edu.njit.cs631.fitness.web.model.ExerciseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.njit.cs631.fitness.data.entity.Exercise;
import edu.njit.cs631.fitness.data.repository.ExerciseRepository;
import edu.njit.cs631.fitness.service.api.ExerciseService;

@Service("exerciseService")
public class ExerciseServiceImpl implements ExerciseService {
	
	@Autowired
	private ExerciseRepository exerciseRepository;

	@Autowired
    private ClazzService clazzService;

    @Autowired
    private ClazzRepository clazzRepository;

	public ExerciseServiceImpl() {
		super();
	}

	@Override
	public List<Exercise> listAllExercises() {
		// TODO maybe add sort 
		return exerciseRepository.findAll();
	}

	@Override
    public void deleteExercise(Integer id) {
	    Exercise exercise = exerciseRepository.findOne(id);

	    if (exercise == null) return;

	    List<Clazz> clazzes = clazzRepository.findByExercise(exercise);

	    for(Clazz clazz : clazzes) {
	        clazzRepository.delete(clazz);
        }

        clazzRepository.flush();

	    exerciseRepository.delete(exercise);
	    exerciseRepository.flush();

    }

    @Override
    public void addNewExercise(ExerciseModel model) {
	    Exercise exercise = new Exercise();
	    exercise.setDescription(model.getDescription());
	    exercise.setName(model.getExerciseName());

	    exerciseRepository.saveAndFlush(exercise);
    }

}
