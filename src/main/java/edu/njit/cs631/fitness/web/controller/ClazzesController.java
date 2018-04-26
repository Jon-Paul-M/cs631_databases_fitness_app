package edu.njit.cs631.fitness.web.controller;


import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.service.api.ClazzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ClazzesController {

    private static final Logger logger = LoggerFactory.getLogger(ClazzesController.class);

    @Autowired
    private ClazzService clazzService;


    @RequestMapping(value = {"/classes"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String clazzes(Model model) {
        List<Clazz> clazzes = clazzService.listFutureActiveClasses();
        model.addAttribute("clazzes", clazzes );

        return "classes";
    }
}
