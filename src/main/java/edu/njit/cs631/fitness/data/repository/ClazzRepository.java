package edu.njit.cs631.fitness.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.njit.cs631.fitness.data.entity.Clazz;

public interface ClazzRepository extends JpaRepository<Clazz, Integer> {

}
