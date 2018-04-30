package edu.njit.cs631.fitness.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {


    @RequestMapping(value = {"/", "/home", "/login", "/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView home() {
        return commonModelAndView();
    }

    @Override
    protected String getCommonViewName() {
        return "home";
    }
}
