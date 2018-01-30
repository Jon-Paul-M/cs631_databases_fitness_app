package edu.njit.cs631.example.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value="/example")
public class SubscribeController {

	Logger logger = LoggerFactory.getLogger(getClass());
	public SubscribeController() {
		super();
	}
	
	@RequestMapping(value = "/subscribe", method = RequestMethod.GET)
	public ModelAndView subscribe() {
		return new ModelAndView("example/subscribe");
	}
	
	@RequestMapping(value = "/subscribe", method = RequestMethod.POST)
	public ModelAndView submit() {
		logger.info("Do something awesome here!");
		return new ModelAndView("example/subscribe");
	}
}
