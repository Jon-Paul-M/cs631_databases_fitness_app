package edu.njit.cs631.fitness.web.controller;


import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.repository.ClazzRepository;
import edu.njit.cs631.fitness.service.api.ClazzAdministrationService;
import edu.njit.cs631.fitness.service.api.ClazzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
public class ClazzesController extends BaseController {

    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ClazzesController.class);

    @SuppressWarnings("unused")
    @Autowired
    private ClazzAdministrationService clazzAdministrationService;

    @Autowired
    private ClazzRepository clazzRepository;


    @RequestMapping(value = {"/classes"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView clazzes() {
        return commonModelAndView();
    }


    @RequestMapping(value="/classes/recommend", method = RequestMethod.GET)
    public ModelAndView clazzRecommendation(
            @RequestParam(value="id", required=false, defaultValue="-1") Integer id) {

        ModelAndView err = new ModelAndView("redirect:/classes");
        if (id == -1) return err;
        Clazz clazz = clazzRepository.findOne(id);
        if(clazz == null) return err;

        List<Clazz> sameExercisesClazzes = clazzService.listSameExerciseClazzes(id);
        List<Clazz> sameTimeWindowClazzes = clazzService.listSameTimeWindowClazzes(id);
        List<Clazz> sameInstructorClazzes = clazzService.listSameInstructorClazzes(id);

        ModelAndView mv = commonModelAndView("recommendations");
        mv.addObject("currentClazz", Arrays.asList(clazz));
        mv.addObject("sameExercisesClazzes", sameExercisesClazzes);
        mv.addObject("sameTimeWindowClazzes", sameTimeWindowClazzes);
        mv.addObject("sameInstructorClazzes", sameInstructorClazzes);
        return mv;

    }


    @RequestMapping(value = {"/classes/register"}, method = RequestMethod.GET)
    public String registerGet(
            @RequestParam(value="self", required=false, defaultValue="false") boolean self,
            @RequestParam(value="class", required=false, defaultValue="0") Integer clazzId) {
        if (hasAuthority("REGISTER_SELF_FOR_CLASS") && self && clazzId > 0) {
            clazzAdministrationService.registerUserForClass(getCurrentUser().getId(), clazzId);
        } else {
            logger.info("NO SELF");
        }

        return "redirect:/classes";
    }

    @RequestMapping(value = {"/classes/register"}, method = RequestMethod.POST)
    public String register() {
        return "redirect:/classes";
    }

    @RequestMapping(value = {"/classes/deregister"}, method = RequestMethod.GET)
    public String deregisterGet(
            @RequestParam(value="self", required=false, defaultValue="false") boolean self,
            @RequestParam(value="class", required=false, defaultValue="0") Integer clazzId) {
        if (hasAuthority("REGISTER_SELF_FOR_CLASS") && self && clazzId > 0) {
            clazzAdministrationService.deregisterUserForClass(getCurrentUser().getId(), clazzId);
        } else {
            logger.info("NO SELF");
        }

        return "redirect:/classes";
    }

    @RequestMapping(value = {"/classes/deregister"}, method = RequestMethod.POST)
    public String deregister() {
        return "redirect:/classes";
    }

    @Override
    protected String getCommonViewName() {
        return "classes";
    }
}
