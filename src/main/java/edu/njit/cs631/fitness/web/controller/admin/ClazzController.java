package edu.njit.cs631.fitness.web.controller.admin;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.njit.cs631.fitness.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.cs631.fitness.service.api.ClazzAdministrationService;
import edu.njit.cs631.fitness.web.model.ClazzModel;
import edu.njit.cs631.fitness.web.model.MonthModel;

import javax.validation.Valid;

@Controller
@RequestMapping(value="/admin/classes")
public class ClazzController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ClazzAdministrationService clazzAdministrationService;

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
	
	public ClazzController() {
		super();
		years = new ArrayList<>();
		Integer currectYear = Calendar.getInstance().get(Calendar.YEAR);
		for (Integer year = currectYear; year < currectYear + 10; year++) {
			years.add(year);
		}
	}

    @Override
    protected String getCommonViewName() {
        return "admin/classes/create";
    }

	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView get() {
		logger.info("In ClazzController.get");
		ClazzModel clazzModel = defaultClazz();
		ModelAndView modelAndView = commonModelAndView();
		modelAndView.addObject("clazzModel", clazzModel);
		addClazzes(modelAndView);
		return modelAndView;
	}	
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView post(@Valid ClazzModel clazzModel, BindingResult result, ModelAndView m) {
		logger.info("In ClazzController.post");
		logger.info(clazzModel.toString());

		if(result.hasErrors()) {
            ModelAndView mv = commonModelAndView(m);
            mv.addObject("clazzModel", clazzModel);
            return mv;
        }

        clazzModel.setStartTime(parseStart(clazzModel));

		if(valid(clazzModel, result)) {
			LocalDateTime start = parseStart(clazzModel);
			Double duration = clazzModel.getDuration();
			clazzAdministrationService.createClass(clazzModel.getExercise(),
                                                   clazzModel.getInstructor(),
                                                   clazzModel.getRoom(),
                                                   start,
                                                   duration);
            clazzModel = defaultClazz();
		}

		return commonModelAndView().addObject("clazzModel", clazzModel);

	}


	private LocalDateTime parseStart(ClazzModel clazzModel) {
		Calendar calendar = Calendar.getInstance();
		int year = Integer.parseInt(clazzModel.getStartYYYY());
		int month = Integer.parseInt(clazzModel.getStartMM()) - 1;
		int date = Integer.parseInt(clazzModel.getStartDD());
		int hourOfDay = Integer.parseInt(clazzModel.getStartHH());
		hourOfDay += clazzModel.getStartMeridiem().equals(MERIDIEMS.get(0)) ? 0 : 12;
		int minute = Integer.parseInt(clazzModel.getStartMI());
		calendar.set(year, month, date, hourOfDay, minute, 0);
		LocalDateTime start = LocalDateTime.ofInstant(
		        Instant.ofEpochMilli(calendar.getTimeInMillis()), ZoneId.systemDefault());
		return start;
	}

    @Override
    protected ModelAndView commonModelAndView(ModelAndView mv) {
        ModelAndView modelAndView = super.addParams(super.commonModelAndView(mv));
        modelAndView.addObject("months", MONTHS);
        modelAndView.addObject("days", DAYS);
        modelAndView.addObject("years", years);
        modelAndView.addObject("hours", HOURS);
        modelAndView.addObject("minuets", MINUETS);
        modelAndView.addObject("meridiems", MERIDIEMS);
        return modelAndView;
    }

	@Override
	protected ModelAndView commonModelAndView() {
	    return commonModelAndView(super.commonModelAndView());
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

	private boolean valid(ClazzModel clazzModel, BindingResult result) {
		// TODO probably call something in the service level
		// that throws an exception if invalid

        if (clazzModel.getStartTime().isBefore(LocalDateTime.now())) {
            result.addError(new ObjectError("Start Time", "Start time must be in the future"));
            return false;
        }

        return true;

	}
}

