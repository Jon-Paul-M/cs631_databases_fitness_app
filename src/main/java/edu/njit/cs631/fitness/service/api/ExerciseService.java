package edu.njit.cs631.fitness.service.api;

import java.util.List;

import edu.njit.cs631.fitness.data.entity.Exercise;
import edu.njit.cs631.fitness.web.model.ExerciseModel;

public interface ExerciseService {
	public List<Exercise> listAllExercises();

	void deleteExercise(Integer id);
    void addNewExercise(ExerciseModel model);
    void editExercise(ExerciseModel exerciseModel);
}
