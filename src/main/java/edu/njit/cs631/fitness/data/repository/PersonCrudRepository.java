package edu.njit.cs631.fitness.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.njit.cs631.fitness.data.entity.Person;

public interface PersonCrudRepository extends CrudRepository<Person, Integer> {

	List<Person> findByLastName(String lastName);

	Person findByEmail(String email);
}
