package edu.njit.cs631.fitness.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import edu.njit.cs631.fitness.data.entity.*;
import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.data.repository.MemberRepository;
import edu.njit.cs631.fitness.data.repository.security.UserRepository;
import edu.njit.cs631.fitness.service.api.ClazzService;
import edu.njit.cs631.fitness.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.njit.cs631.fitness.data.repository.ClazzRepository;
import edu.njit.cs631.fitness.data.repository.ExerciseRepository;
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
    private UserRepository userRepository;

	@Autowired
    private ClazzService clazzService;

	@Autowired
    private MemberRepository memberRepository;

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private ClazzRepository clazzRepository;

	public ClazzAdministrationServiceImpl() {
		super();
	}

	private final Random random = new Random();

    @Override
    public void generateRandomClasses() {
        List<Room> rooms = roomRepository.findAll();
        List<Instructor> instructors = userService.listAllInstructors();
        List<Exercise> exercises = exerciseRepository.findAll();

        int hoursOffset = 0;
        int daysOffset = 0;

        // We need the number of instructors to be greater than the number of
        while(instructors.size() > rooms.size()) {
            instructors.remove(0);
        }

        LocalDateTime currentOffset = LocalDateTime.now().plusMinutes(30);
        // we'll create one class for every room with a random instructor
        for(int i = 0; i < 3; i++) {
            Collections.shuffle(instructors, random);
            for(Room room : rooms) {
                Exercise exercise = exercises.get(random.nextInt(exercises.size()));
                for(Instructor instructor : instructors) {
                    createClass(exercise.getId(), instructor.getId(), room.getId(), currentOffset, random.nextDouble());
                }
            }
            currentOffset = currentOffset.plusHours(1);
        }

    }

    @Override
    public void generateRandomRegistrations() {
        Set<Integer> all_users = userRepository.findAll()
                .stream()
                .map(User::getId).collect(Collectors.toSet());

        Set<Integer> all_members = memberRepository.findAll()
                .stream()
                .map(User::getId).collect(Collectors.toSet());

        List<Clazz> clazzes = clazzService.listFutureActiveClasses();

        for(Clazz clazz : clazzes) {
            Set<Integer> registered_users = clazz.getMembers()
                    .stream()
                    .map(User::getId)
                    .collect(Collectors.toSet());
            if(clazz.getCapacity() > registered_users.size()) {
                int remainingCapacity = clazz.getCapacity() - registered_users.size();
                Set<Integer> all_unregistered_members = new HashSet<>(all_members);
                all_unregistered_members.removeAll(registered_users);
                if(all_unregistered_members.size() == 0) {
                    continue;
                }

                int numberToFill = random.nextInt(remainingCapacity);


                List<Integer> chosen = new ArrayList<>(all_unregistered_members);
                Collections.shuffle(chosen, random);

                if(chosen.size() > numberToFill) {
                    chosen = chosen.subList(0, numberToFill);
                }

                for(Integer userId : chosen) {
                    registerUserForClass(userId, clazz.getId());
                }

            }

        }

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
    public void deleteClazz(Integer clazzId) {
        Clazz clazz = clazzRepository.findOne(clazzId);

        if(clazz.getMembers().size() > 0) {
            clazz.setMembers(new HashSet<>());
            clazzRepository.saveAndFlush(clazz);
        }

        clazzRepository.delete(clazz);
        clazzRepository.flush();
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
