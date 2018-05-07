package edu.njit.cs631.fitness.service.api;

import java.util.List;

import edu.njit.cs631.fitness.data.entity.Clazz;

public interface ClazzService {
	List<Clazz> listFutureActiveClasses();

	List<Clazz> listSameInstructorClazzes(Integer clazzId);
	List<Clazz> listSameTimeWindowClazzes(Integer clazzId);
	List<Clazz> listSameExerciseClazzes(Integer clazzId);
}
