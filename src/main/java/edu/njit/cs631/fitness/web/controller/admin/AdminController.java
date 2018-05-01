package edu.njit.cs631.fitness.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.cs631.fitness.web.controller.BaseController;

@Controller
public class AdminController extends BaseController {

    @Override
    protected String getCommonViewName() {
        return "admin";
    }

    @RequestMapping(value="/admin", method= RequestMethod.GET)
    public ModelAndView admin() {
        return commonModelAndView();
    }

}
