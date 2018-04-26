package edu.njit.cs631.fitness.web.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.cs631.fitness.data.entity.Room;
import edu.njit.cs631.fitness.service.api.ClazzAdministrationService;
import edu.njit.cs631.fitness.service.api.ClazzService;
import edu.njit.cs631.fitness.service.api.ExerciseService;
import edu.njit.cs631.fitness.service.api.InstructorService;
import edu.njit.cs631.fitness.service.api.RoomService;
import edu.njit.cs631.fitness.web.model.ClazzModel;

@Controller
@RequestMapping(value="/admin/classes")
public class CreateClazzController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ClazzAdministrationService clazzAdministrationService;

	@Autowired
	private ClazzService clazzService;
	
	@Autowired
	private InstructorService instructorService;

	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private RoomService roomService;
	
	private static final List<Integer> MONTHS = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
	private static final List<Integer> DAYS = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31);
	private static final List<Integer> HOURS = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
	private static final List<String> MINUETS = Arrays.asList("00","15", "30", "45");
	private static final List<String> MERIDIEMS = Arrays.asList("AM","PM");
	private static List<Integer> years;
	
	public CreateClazzController() {
		super();
		years = new ArrayList<>();
		Integer currectYear = Calendar.getInstance().get(Calendar.YEAR);
		for (Integer year = currectYear; year < currectYear + 10; year++) {
			years.add(year);
		}
	}

	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView get() {
		logger.info("In CreateClazzController.get");
		ModelAndView modelAndView = commonModelAndView(new ClazzModel());
		return modelAndView;
	}

	
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView post(@ModelAttribute ClazzModel clazzModel) {
		logger.info("In CreateClazzController.post");
		logger.info(clazzModel.toString());
		validate(clazzModel);
		Integer exerciseId = 1;
		Integer instructorId = 1;
		Integer roomId = 1;
		Date start = Calendar.getInstance().getTime();
		Integer duration = 90;
		clazzAdministrationService.createClass(exerciseId, instructorId, roomId, start, duration);
		
		ModelAndView modelAndView = commonModelAndView(clazzModel);
		return modelAndView;
	}

	private ModelAndView commonModelAndView(ClazzModel clazz) {
		ModelAndView modelAndView = new ModelAndView("admin/classes/create");
		modelAndView.addObject("clazzModel", clazz);
		modelAndView.addObject("clazzes", clazzService.listFutureActiveClasses());
		modelAndView.addObject("exercises", exerciseService.listAllExercises());
		modelAndView.addObject("instructors", instructorService.listAllInstructors());
		modelAndView.addObject("months", MONTHS);
		modelAndView.addObject("days", DAYS);
		modelAndView.addObject("years", years);
		modelAndView.addObject("hours", HOURS);
		modelAndView.addObject("minuets", MINUETS);
		modelAndView.addObject("meridiems", MERIDIEMS);
		List<Room> rooms = roomService.listAllRooms();
		logger.debug("rooms.size: " + rooms.size());
		modelAndView.addObject("rooms", rooms);
		return modelAndView;
	}
	private void validate(ClazzModel clazzModel) {
		// TODO probably call something in the service level 
		// that throws an exception if invalid
	}
}

