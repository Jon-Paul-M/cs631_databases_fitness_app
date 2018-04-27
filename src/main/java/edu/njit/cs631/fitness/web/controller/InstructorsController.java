package edu.njit.cs631.fitness.web.controller;

import edu.njit.cs631.fitness.data.entity.Instructor;
import edu.njit.cs631.fitness.service.api.InstructorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class InstructorsController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private InstructorService instructorService;

    @RequestMapping(value = {"/instructors"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String home(Model model) {
        List<Instructor> instructors = instructorService.listAllInstructors();
        model.addAttribute("instructors", instructors );

        return "instructors";
    }
}