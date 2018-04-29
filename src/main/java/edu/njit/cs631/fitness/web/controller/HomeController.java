package edu.njit.cs631.fitness.web.controller;

import edu.njit.cs631.fitness.data.entity.Instructor;
import edu.njit.cs631.fitness.data.entity.Membership;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import edu.njit.cs631.fitness.service.api.InstructorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
