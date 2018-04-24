package edu.njit.cs631.fitness.data.repository;

import org.springframework.data.repository.CrudRepository;

import edu.njit.cs631.fitness.data.entity.HourlyInstructor;

public interface HourlyInstructorCrudRepository extends CrudRepository<HourlyInstructor, Integer> {

}
