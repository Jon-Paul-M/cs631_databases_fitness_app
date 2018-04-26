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

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.entity.Room;
import edu.njit.cs631.fitness.service.api.ClazzAdministrationService;
import edu.njit.cs631.fitness.service.api.ClazzService;
import edu.njit.cs631.fitness.service.api.ExerciseService;
import edu.njit.cs631.fitness.service.api.InstructorService;
import edu.njit.cs631.fitness.service.api.RoomService;
import edu.njit.cs631.fitness.web.model.ClazzModel;
import edu.njit.cs631.fitness.web.model.MonthModel;

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
	
	private static final List<MonthModel> MONTHS = Arrays.asList(new MonthModel( 1, "January"), 
			                                                     new MonthModel( 2, "February"),
			                                                     new MonthModel( 3, "March"),
			                                                     new MonthModel( 4, "April"),
			                                                     new MonthModel( 5, "May"),
			                                                     new MonthModel( 6, "June"),
			                                                     new MonthModel( 7, "July"),
			                                                     new MonthModel( 8, "August"),
			                                                     new MonthModel( 9, "September"),
			                                                     new MonthModel(10, "October"),
			                                                     new MonthModel(11, "November"),
			                                                     new MonthModel(12, "December"));
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
		ClazzModel clazzModel = defaultClazz();
		ModelAndView modelAndView = commonModelAndView();
		modelAndView.addObject("clazzModel", clazzModel);
		addClazzes(modelAndView);
		return modelAndView;
	}	
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView post(@ModelAttribute ClazzModel clazzModel) {
		logger.info("In CreateClazzController.post");
		logger.info(clazzModel.toString());
		ModelAndView modelAndView = commonModelAndView();
		if(valid(clazzModel)) {
			Integer exerciseId = Integer.parseInt(clazzModel.getExercise());
			Integer instructorId = Integer.parseInt(clazzModel.getInstructor());
			Integer roomId = Integer.parseInt(clazzModel.getRoom());
			Date start = parseStart(clazzModel);
			Integer duration = 90;
			clazzAdministrationService.createClass(exerciseId, instructorId, roomId, start, duration);
			clazzModel = defaultClazz();
		}
		modelAndView.addObject("clazzModel", clazzModel);
		addClazzes(modelAndView);
		return modelAndView;
	}

	private void addClazzes(ModelAndView modelAndView) {
		List<Clazz> futureActiveClasses = clazzService.listFutureActiveClasses();
		logger.info("futureActiveClasses.size(): " + futureActiveClasses.size());
		modelAndView.addObject("clazzes", futureActiveClasses);
	}

	private Date parseStart(ClazzModel clazzModel) {
		Calendar calendar = Calendar.getInstance();
		int year = Integer.parseInt(clazzModel.getStartYYYY());
		int month = Integer.parseInt(clazzModel.getStartMM()) - 1;
		int date = Integer.parseInt(clazzModel.getStartDD());
		int hourOfDay = Integer.parseInt(clazzModel.getStartHH());
		hourOfDay += clazzModel.getStartMeridiem().equals(MERIDIEMS.get(0)) ? 0 : 12;
		int minute = Integer.parseInt(clazzModel.getStartMI());
		calendar.set(year, month, date, hourOfDay, minute, 0);
		Date start = calendar.getTime();
		return start;
	}

	private ModelAndView commonModelAndView() {
		ModelAndView modelAndView = new ModelAndView("admin/classes/create");
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
	
	private ClazzModel defaultClazz() {
		ClazzModel clazz = new ClazzModel();
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		clazz.setStartMM("" + MONTHS.get(tomorrow.get(Calendar.MONTH)).getIndex());
		clazz.setStartDD("" + tomorrow.get(Calendar.DAY_OF_MONTH));
		clazz.setStartYYYY("" + tomorrow.get(Calendar.YEAR));
		clazz.setStartHH("8");
		clazz.setStartMI("30");
		clazz.setStartMeridiem(MERIDIEMS.get(0));
		return clazz;
	}

	private boolean valid(ClazzModel clazzModel) {
		return true;
		// TODO probably call something in the service level 
		// that throws an exception if invalid
	}
} // test 2

