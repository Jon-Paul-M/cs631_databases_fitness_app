package edu.njit.cs631.fitness.usecases.mvp.admin_user;

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.entity.Exercise;
import edu.njit.cs631.fitness.data.entity.HourlyInstructor;
import edu.njit.cs631.fitness.data.entity.Member;
import edu.njit.cs631.fitness.data.entity.Room;
import edu.njit.cs631.fitness.data.repository.ExerciseRepository;
import edu.njit.cs631.fitness.data.repository.HourlyInstructorRepository;
import edu.njit.cs631.fitness.data.repository.RoomRepository;
import edu.njit.cs631.fitness.service.api.ClazzAdministrationService;
import edu.njit.cs631.fitness.service.api.ClazzService;
import edu.njit.cs631.fitness.testutils.BaseTest;
import edu.njit.cs631.fitness.web.error.InstructorConflictException;
import edu.njit.cs631.fitness.web.error.RoomConflictException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

@Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-h2.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class T_014_OverlappingClasses_Test extends BaseTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ExerciseRepository exerciseRepository;
	
	@Autowired
	private HourlyInstructorRepository hourlyInstructorRepository;
	
	@Autowired
	private RoomRepository roomRepository;

    @Autowired
    private ClazzAdministrationService clazzAdministrationService;

    @Autowired
    private ClazzService clazzService;

    @Test(expected = InstructorConflictException.class)
    public void createClass_OverlappingInstructor_ServiceThrowsException() throws Exception {
    	Exercise exercise = ((List<Exercise>) exerciseRepository.findAll()).get(0);
    	HourlyInstructor instructor = ((List<HourlyInstructor>) hourlyInstructorRepository.findAll()).get(0);
    	Room room = ((List<Room>) roomRepository.findAll()).get(0);
    	LocalDateTime start = LocalDateTime.ofInstant(
    			Instant.ofEpochMilli(Calendar.getInstance().getTimeInMillis()),
				ZoneId.systemDefault());
    	Integer duration = 60;
    	Clazz clazz1 = clazzAdministrationService.createClass(exercise.getId(), instructor.getId(), room.getId(), start, duration);
    	logger.info("jpm: " + clazz1.toString());
    	LocalDateTime start2 = start.plusMinutes(1);
    	Clazz clazz2 = clazzAdministrationService.createClass(exercise.getId(), instructor.getId(), room.getId(), start2, duration);
    	logger.info("jpm2: " + clazz2.toString());
    }
    
    
    @Test(expected = RoomConflictException.class)
    public void createClass_OverlappingRoom_ServiceThrowsException() throws Exception {
    	Exercise exercise = ((List<Exercise>) exerciseRepository.findAll()).get(0);
    	List<HourlyInstructor> instructors = (List<HourlyInstructor>) hourlyInstructorRepository.findAll();
		HourlyInstructor instructor1 = instructors.get(0);
    	HourlyInstructor instructor2 = instructors.get(1);
    	Room room = ((List<Room>) roomRepository.findAll()).get(0);
    	LocalDateTime start = LocalDateTime.ofInstant(
    			Instant.ofEpochMilli(Calendar.getInstance().getTimeInMillis()),
				ZoneId.systemDefault());
    	start = start.plusDays(1);
    	Integer duration = 60;
    	Clazz clazz1 = clazzAdministrationService.createClass(exercise.getId(), instructor1.getId(), room.getId(), start, duration);
    	logger.info("jpm3: " + clazz1.toString());
    	LocalDateTime start2 = start.plusMinutes(1);
    	Clazz clazz2 = clazzAdministrationService.createClass(exercise.getId(), instructor2.getId(), room.getId(), start2, duration);
    	logger.info("jpm4: " + clazz2.toString());
    }

    @Test
    public void reviewDetail_Member_CanViewDetail() throws Exception {
        Member member = userService.findMemberByEmail("member@test.com");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/members/details?id=" + member.getId())
                        .accept(MediaType.TEXT_HTML)
                        .with(user(getAdminUser())))
                .andExpect(status().isOk());
    }
}
