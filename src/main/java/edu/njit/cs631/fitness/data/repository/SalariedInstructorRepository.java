package edu.njit.cs631.fitness.data.repository;

import edu.njit.cs631.fitness.data.entity.SalariedInstructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalariedInstructorRepository extends JpaRepository<SalariedInstructor, Integer>{
}
