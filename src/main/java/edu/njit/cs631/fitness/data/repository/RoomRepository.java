package edu.njit.cs631.fitness.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.njit.cs631.fitness.data.entity.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findByBuildingName(String buildingName);

}
