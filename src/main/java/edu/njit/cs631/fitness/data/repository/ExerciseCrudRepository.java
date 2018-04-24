package edu.njit.cs631.fitness.data.repository;

import org.springframework.data.repository.CrudRepository;

import edu.njit.cs631.fitness.data.entity.Exercise;

public interface ExerciseCrudRepository extends CrudRepository<Exercise, Integer> {

}
