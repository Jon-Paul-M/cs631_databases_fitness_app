package edu.njit.cs631.fitness.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import javax.transaction.Transactional;

import edu.njit.cs631.fitness.data.entity.*;
import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.service.api.UserService;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private UserService userService;

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private ClazzRepository clazzRepository;

	public ClazzAdministrationServiceImpl() {
		super();
	}

	@Override
	@Transactional
	public Clazz createClass(Integer exerciseId, Integer instructorId, Integer roomId, LocalDateTime start, Double duration) {
		logger.info("In clazzAdministrationService.createClass");
    	Exercise exercise = exerciseRepository.findOne(exerciseId);
    	Instructor instructor = userService.findInstructor(instructorId);
    	Room room = roomRepository.findOne(roomId);
    	Clazz clazz = new Clazz();
    	clazz.setExercise(exercise);
    	clazz.setInstructor((User)instructor);
    	clazz.setRoom(room);
    	clazz.setStart(Timestamp.valueOf(start));
    	clazz.setDuration(duration);
		return clazzRepository.saveAndFlush(clazz);
	}


	@Override
    @Transactional
	public void registerUserForClass(Integer userId, Integer clazzId) {
		Clazz clazz = clazzRepository.findOne(clazzId);
        if (clazz == null) {
            return;
        }
		if (clazz.getStart().toLocalDateTime().isBefore(LocalDateTime.now())) {
		    // can't change a registration in the past
		    return;
        }

        User user = userService.findUser(userId);

		if (user == null) {
		    // no such user, maybe throw exception.
		    return;
        }

        Set<User> users = clazz.getMembers();

		if (!users.contains(user)) {
            users.add(user);

            clazz.setMembers(users);
            clazzRepository.saveAndFlush(clazz);
		}
	}


    @Override
    @Transactional
    public void deregisterUserForClass(Integer userId, Integer clazzId) {
        Clazz clazz = clazzRepository.findOne(clazzId);
        if (clazz == null) {
            return;
        }


        if (clazz.getStart().toLocalDateTime().isBefore(LocalDateTime.now())) {
            // can't change a registration in the past
            return;
        }

        User user = userService.findUser(userId);

        if (user == null) {
            // no such user, maybe throw exception.
            return;
        }

        Set<User> users = clazz.getMembers();

        if (users.contains(user)) {
            users.remove(user);

            clazz.setMembers(users);
            clazzRepository.saveAndFlush(clazz);
        }
    }

    // TODO: How are we doing this?
    // See https://stackoverflow.com/questions/17106670/how-to-check-a-timeperiod-is-overlapping-another-time-period-in-java
    private boolean timePeriodsOverlap(LocalDateTime startA,
                                       LocalDateTime stopA,
                                       LocalDateTime startB,
                                       LocalDateTime stopB) {
	    return (
                    ( startA.isBefore( stopB ) )
                    &&
                    ( stopA.isAfter( startB ) )
                ) ;
    }

}
