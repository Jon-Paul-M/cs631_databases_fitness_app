package edu.njit.cs631.fitness.service.api;

import java.util.List;

import edu.njit.cs631.fitness.data.entity.Room;
import edu.njit.cs631.fitness.web.model.RoomModel;

public interface RoomService {
	public List<Room> listAllRooms();

	void addNewRoom(RoomModel roomModel);

    void deleteRoom(Integer id);
}
