package edu.njit.cs631.fitness.usecases.mvp.admin_user;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.entity.Exercise;
import edu.njit.cs631.fitness.data.entity.HourlyInstructor;
import edu.njit.cs631.fitness.data.entity.Room;
import edu.njit.cs631.fitness.data.repository.ExerciseRepository;
import edu.njit.cs631.fitness.data.repository.HourlyInstructorRepository;
import edu.njit.cs631.fitness.data.repository.RoomRepository;
import edu.njit.cs631.fitness.service.api.ClazzAdministrationService;
import edu.njit.cs631.fitness.testutils.BaseTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class T_005_CanDefineNewClasses_Test extends BaseTest {
    // UnitOfWork_StateUnderTest_ExpectedBehavior
    
	@Autowired
	private ExerciseRepository exerciseRepository;
	
	@Autowired
	private HourlyInstructorRepository hourlyInstructorRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private ClazzAdministrationService clazzAdministrationService;

	@Test
	@Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
			executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void adminGetClassCreationForm() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders
						.get("/admin/classes/create")
						.accept(MediaType.TEXT_HTML)
						.with(user(getAdminUser())))
				.andExpect(status().isOk());
	}

	@Test
    public void findExercises_ExercisesExist_RepoReturnsExercises() {
    	int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, Exercise.TABLE_NAME);
    	List<Exercise> exercises = (List<Exercise>) exerciseRepository.findAll();
    	Assert.assertNotNull("exercises should never be null", exercises);
    	Assert.assertNotEquals("exercises should not be empty", 0, exercises.size());
    	Assert.assertEquals("number of rows should should equal number of entities", rowCount, exercises.size());
	}

	@Test 
    public void findHourlyInstructor_HourlyInstructorExist_RepoReturnsHourlyInstructor() {
    	int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, HourlyInstructor.TABLE_NAME);
    	List<HourlyInstructor> instructors = (List<HourlyInstructor>) hourlyInstructorRepository.findAll();
    	Assert.assertNotNull("instructors should never be null", instructors);
    	Assert.assertNotEquals("instructors should not be empty", 0, instructors.size());
    	Assert.assertEquals("number of rows should should equal number of entities", rowCount, instructors.size());
	}
	
	@Test 
    public void findRooms_RoomsExist_RepoReturnsRooms() {
    	int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, Room.TABLE_NAME);
    	List<Room> rooms = (List<Room>) roomRepository.findAll();
    	Assert.assertNotNull("rooms should never be null", rooms);
    	Assert.assertNotEquals("rooms should not be empty", 0, rooms.size());
    	Assert.assertEquals("number of rows should should equal number of entities", rowCount, rooms.size());
	}
	
	@Test 
    public void createClass_HappyPath_ClassIsCreated() {
    	int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, Clazz.TABLE_NAME);
    	Exercise exercise = ((List<Exercise>) exerciseRepository.findAll()).get(0);
    	HourlyInstructor instructor = ((List<HourlyInstructor>) hourlyInstructorRepository.findAll()).get(0);
    	Room room = ((List<Room>) roomRepository.findAll()).get(0);
    	LocalDateTime start = LocalDateTime.ofInstant(
    			Instant.ofEpochMilli(Calendar.getInstance().getTimeInMillis()),
				ZoneId.systemDefault());
    	Integer duration = 55;
    	Clazz clazz = clazzAdministrationService.createClass(exercise.getId(), instructor.getId(), room.getId(), start, duration);
    	Assert.assertNotNull("clazz should not be null", clazz);
    	Assert.assertNotNull("clazz.getId() should not be null", clazz.getId());
    	Assert.assertEquals("there should be one row in the table " + Clazz.TABLE_NAME, rowCount + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, Clazz.TABLE_NAME));
	}
}
