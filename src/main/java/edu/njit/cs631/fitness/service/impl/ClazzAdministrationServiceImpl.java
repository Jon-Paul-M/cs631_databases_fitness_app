package edu.njit.cs631.fitness.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.entity.Exercise;
import edu.njit.cs631.fitness.data.entity.HourlyInstructor;
import edu.njit.cs631.fitness.data.entity.Room;
import edu.njit.cs631.fitness.data.repository.ClazzRepository;
import edu.njit.cs631.fitness.data.repository.ExerciseRepository;
import edu.njit.cs631.fitness.data.repository.HourlyInstructorRepository;
import edu.njit.cs631.fitness.data.repository.RoomRepository;
import edu.njit.cs631.fitness.service.api.ClazzAdministrationService;

@Service("clazzAdministrationService")
public class ClazzAdministrationServiceImpl implements ClazzAdministrationService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ExerciseRepository exerciseRepository;
	
	@Autowired
	private HourlyInstructorRepository hourlyInstructorRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private ClazzRepository clazzRepository;

	public ClazzAdministrationServiceImpl() {
		super();
	}

	@Override
	@Transactional
	public Clazz createClass(Integer exerciseId, Integer instructorId, Integer roomId, Date start, Integer duration) {
		logger.info("In clazzAdministrationService.createClass");
    	Exercise exercise = exerciseRepository.findOne(exerciseId);
    	HourlyInstructor instructor = hourlyInstructorRepository.findOne(instructorId);
    	Room room = roomRepository.findOne(roomId);
    	Clazz clazz = new Clazz();
    	clazz.setExercise(exercise);
    	clazz.setInstructor(instructor);
    	clazz.setRoom(room);
    	clazz.setStart(start);
    	clazz.setDuration(duration);
		return clazzRepository.saveAndFlush(clazz);
	}

}
