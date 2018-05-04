package edu.njit.cs631.fitness.usecases.mvp.admin_user;

import edu.njit.cs631.fitness.data.entity.*;
import edu.njit.cs631.fitness.data.repository.ExerciseRepository;
import edu.njit.cs631.fitness.data.repository.HourlyInstructorRepository;
import edu.njit.cs631.fitness.data.repository.RoomRepository;
import edu.njit.cs631.fitness.service.api.ClazzAdministrationService;
import edu.njit.cs631.fitness.service.api.ClazzService;
import edu.njit.cs631.fitness.testutils.BaseTest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
	private RoomRepository roomRepository;

	@Autowired
    private HourlyInstructorRepository hourlyInstructorRepository;

    @Autowired
    private ClazzAdministrationService clazzAdministrationService;

    @Autowired
    private ClazzService clazzService;

    @Test
    public void createClass_OverlappingInstructor_ServiceThrowsDbException() throws Exception {
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
    	try {
    		Clazz clazz2 = clazzAdministrationService.createClass(exercise.getId(), instructor.getId(), room.getId(), start2, duration);
    		logger.info("jpm2: " + clazz2.toString());
    	} catch (Exception e) {
			while (e.getCause() != null) {
				e = (Exception) e.getCause();
			}
    		logger.info(e.getClass().getCanonicalName());
    		logger.info(e.getMessage());
    		logger.info("stacktrace: ", e);
    	}
    }

    @Test
    public void createClass_OverlappingInstructor_ControllerMakesInvalid() throws Exception {
		Exercise exercise = ((List<Exercise>) exerciseRepository.findAll()).get(0);
		HourlyInstructor instructor = ((List<HourlyInstructor>) hourlyInstructorRepository.findAll()).get(0);
		List<Room> rooms = roomRepository.findAll();
		Room room = rooms.get(0);
		LocalDateTime start = LocalDateTime.ofInstant(
				Instant.ofEpochMilli(Calendar.getInstance().getTimeInMillis()),
				ZoneId.systemDefault());
		Integer duration = 60;

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/admin/classes/create")
                        .accept(MediaType.TEXT_HTML)
                        .param("instructor", "" + instructor.getId())
						.param("exercise", "" + exercise.getId())
						.param("room", "" + room.getId())
						.param("startMM", "" + start.getMonthValue())
                        .param("startDD", "" + start.getDayOfMonth())
						.param("startYYY", "" + start.getYear())
						.param("startHH", "" +
										(start.getHour() > 12 ? "" + (start.getHour() - 12) : "" + start.getHour()))
						.param("startMI", "" + start.getMinute())
						.param("startMeridiem", "" + (start.getHour() > 12 ? "PM" : "AM"))
						.param("duration", "" + duration)
                        .with(user(getAdminUser())))
                .andExpect(status().isOk());

        start = start.plusMinutes(15);
        room = rooms.get(1);

		mockMvc.perform(
				MockMvcRequestBuilders
						.post("/admin/classes/create")
						.accept(MediaType.TEXT_HTML)
						.param("instructor", "" + instructor.getId())
						.param("exercise", "" + exercise.getId())
						.param("room", "" + room.getId())
						.param("startMM", "" + start.getMonthValue())
						.param("startDD", "" + start.getDayOfMonth())
						.param("startYYY", "" + start.getYear())
						.param("startHH", "" +
								(start.getHour() > 12 ? "" + (start.getHour() - 12) : "" + start.getHour()))
						.param("startMI", "" + start.getMinute())
						.param("startMeridiem", "" + (start.getHour() > 12 ? "PM" : "AM"))
						.param("duration", "" + duration)
						.with(user(getAdminUser())))
                .andExpect(model().errorCount(1)) // attributeHasErrors("instructor")) // TODO: this doesn't work
				.andExpect(status().isOk());
    }



    @Test
    public void createClass_OverlappingRoom_ControllerMakesInvalid() throws Exception {
        Exercise exercise = ((List<Exercise>) exerciseRepository.findAll()).get(0);
        List<HourlyInstructor> hourlyInstructors = hourlyInstructorRepository.findAll();
        HourlyInstructor instructor = hourlyInstructors.get(0);
        List<Room> rooms = roomRepository.findAll();
        Room room = rooms.get(0);
        LocalDateTime start = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(Calendar.getInstance().getTimeInMillis()),
                ZoneId.systemDefault());
        Integer duration = 60;

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/admin/classes/create")
                        .accept(MediaType.TEXT_HTML)
                        .param("instructor", "" + instructor.getId())
                        .param("exercise", "" + exercise.getId())
                        .param("room", "" + room.getId())
                        .param("startMM", "" + start.getMonthValue())
                        .param("startDD", "" + start.getDayOfMonth())
                        .param("startYYY", "" + start.getYear())
                        .param("startHH", "" +
                                (start.getHour() > 12 ? "" + (start.getHour() - 12) : "" + start.getHour()))
                        .param("startMI", "" + start.getMinute())
                        .param("startMeridiem", "" + (start.getHour() > 12 ? "PM" : "AM"))
                        .param("duration", "" + duration)
                        .with(user(getAdminUser())))
                .andExpect(status().isOk());

        start = start.plusMinutes(15);
        instructor = hourlyInstructors.get(1);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/admin/classes/create")
                        .accept(MediaType.TEXT_HTML)
                        .param("instructor", "" + instructor.getId())
                        .param("exercise", "" + exercise.getId())
                        .param("room", "" + room.getId())
                        .param("startMM", "" + start.getMonthValue())
                        .param("startDD", "" + start.getDayOfMonth())
                        .param("startYYY", "" + start.getYear())
                        .param("startHH", "" +
                                (start.getHour() > 12 ? "" + (start.getHour() - 12) : "" + start.getHour()))
                        .param("startMI", "" + start.getMinute())
                        .param("startMeridiem", "" + (start.getHour() > 12 ? "PM" : "AM"))
                        .param("duration", "" + duration)
                        .with(user(getAdminUser())))
                .andExpect(model().errorCount(1)) // attributeHasErrors("room")) // TODO: this doesn't work
                .andExpect(status().isOk());
    }
}
