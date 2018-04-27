package edu.njit.cs631.fitness.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.njit.cs631.fitness.data.entity.Room;
import edu.njit.cs631.fitness.data.repository.RoomRepository;
import edu.njit.cs631.fitness.service.api.RoomService;

@Service("roomService")
public class RoomServiceImpl implements RoomService {
	
	@Autowired
	private RoomRepository roomRepository;

	public RoomServiceImpl() {
		super();
	}

	@Override
	public List<Room> listAllRooms() {
		// TODO maybe add sort 
		return roomRepository.findAll();
	}

}
