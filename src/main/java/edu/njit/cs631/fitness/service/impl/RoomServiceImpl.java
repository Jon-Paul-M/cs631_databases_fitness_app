package edu.njit.cs631.fitness.service.impl;

import java.util.List;

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.repository.ClazzRepository;
import edu.njit.cs631.fitness.web.model.RoomModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.njit.cs631.fitness.data.entity.Room;
import edu.njit.cs631.fitness.data.repository.RoomRepository;
import edu.njit.cs631.fitness.service.api.RoomService;
import sun.reflect.generics.repository.ClassRepository;

@Service("roomService")
public class RoomServiceImpl implements RoomService {
	
	@Autowired
	private RoomRepository roomRepository;

	@Autowired
    private ClazzRepository clazzRepository;

	public RoomServiceImpl() {
		super();
	}

	@Override
	public List<Room> listAllRooms() {
		// TODO maybe add sort 
		return roomRepository.findAll();
	}

	@Override
	public void addNewRoom(RoomModel roomModel) {
		Room room = new Room();
		room.setBuildingName(roomModel.getBuildingName());
		room.setRoomNumber(roomModel.getRoomNumber());
		room.setCapacity(roomModel.getCapacity());

		roomRepository.saveAndFlush(room);
	}

    @Override
    public void deleteRoom(Integer id) {
        Room room = roomRepository.findOne(id);

        if (room == null) return;

        List<Clazz> clazzes = clazzRepository.findByRoom(room);

        for(Clazz clazz : clazzes) {
            clazz.setRoom(null);
            clazzRepository.save(clazz);
        }

        clazzRepository.flush();

        roomRepository.delete(room);
        roomRepository.flush();
    }

}
