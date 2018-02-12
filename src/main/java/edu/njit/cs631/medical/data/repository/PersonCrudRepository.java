package edu.njit.cs631.medical.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.njit.cs631.medical.data.entity.Person;

public interface PersonCrudRepository extends CrudRepository<Person, Long> {

	List<Person> findByLastName(String lastName);
}
