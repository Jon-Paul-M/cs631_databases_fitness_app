package edu.njit.cs631.fitness.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InstructorsController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = {"/instructors"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView home() {
        return commonModelAndView();
    }

    @Override
    protected String getCommonViewName() {
        return "instructors";
    }
}
