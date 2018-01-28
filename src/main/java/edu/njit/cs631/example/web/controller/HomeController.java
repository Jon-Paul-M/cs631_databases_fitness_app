package edu.njit.cs631.example.web.controller;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/example")
public class HomeController {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	public HomeController() {
        super();
    }
	
	@RequestMapping(value="/")
	public ModelAndView home() {
		logger.info("In HomeController.home");
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("today", Calendar.getInstance());
		return modelAndView;
	}
}
