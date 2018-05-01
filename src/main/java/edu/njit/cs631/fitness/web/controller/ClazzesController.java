package edu.njit.cs631.fitness.web.controller;


import edu.njit.cs631.fitness.service.api.ClazzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClazzesController extends BaseController {

    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ClazzesController.class);

    @SuppressWarnings("unused")
    @Autowired
    private ClazzService clazzService;


    @RequestMapping(value = {"/classes"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView clazzes() {
        return commonModelAndView();
    }

    @Override
    protected String getCommonViewName() {
        return "classes";
    }
}
