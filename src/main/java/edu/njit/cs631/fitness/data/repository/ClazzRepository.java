package edu.njit.cs631.fitness.data.repository;

import edu.njit.cs631.fitness.data.entity.Exercise;
import edu.njit.cs631.fitness.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import edu.njit.cs631.fitness.data.entity.Clazz;

import java.util.List;

public interface ClazzRepository extends JpaRepository<Clazz, Integer>, JpaSpecificationExecutor<Clazz> {

    List<Clazz> findByRoom(Room room);
    List<Clazz> findByExercise(Exercise exercise);

}
