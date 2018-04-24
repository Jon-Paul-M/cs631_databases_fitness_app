package edu.njit.cs631.fitness.data.repository;

import org.springframework.data.repository.CrudRepository;

import edu.njit.cs631.fitness.data.entity.Room;

public interface RoomCrudRepository extends CrudRepository<Room, Integer> {

}
