package edu.njit.cs631.example.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/example")
public class UserProfileController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	public UserProfileController() {
		super();
	}

	@RequestMapping(value = "/userprofile")
	public ModelAndView blahBlah() {
		return new ModelAndView();
	}
}
