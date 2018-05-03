package edu.njit.cs631.fitness.web.controller.admin;

import javax.validation.Valid;

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.entity.Instructor;
import edu.njit.cs631.fitness.web.util.ClazzDateComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.cs631.fitness.data.entity.InstructorTypes;
import edu.njit.cs631.fitness.service.api.UserService;
import edu.njit.cs631.fitness.web.controller.BaseController;
import edu.njit.cs631.fitness.web.model.InstructorModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(value="/admin/instructors")
public class CreateInstructorController extends BaseController {

    @Autowired
    private UserService userService;

    @Override
    protected String getCommonViewName() {
        return "admin/instructors/create";
    }

    @Override
    protected ModelAndView addParams(ModelAndView modelAndView) {
        return super.addParams(modelAndView)
                .addObject("instructorTypes", InstructorTypes.values());
    }

    @RequestMapping(value="/create", method= RequestMethod.GET)
    public ModelAndView createInstructor() {
        ModelAndView mv = commonModelAndView();
        mv.addObject("instructorModel", new InstructorModel());
        return mv;
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public ModelAndView createInstructor(@Valid InstructorModel instructorModel, BindingResult result, ModelAndView m) {
        if (result.hasErrors()) {
            ModelAndView mv = commonModelAndView(m);
            mv.addObject("instructorModel", instructorModel);
            return mv;
        }

        userService.registerNewInstructorAccount(instructorModel);

        ModelAndView mv = new ModelAndView("redirect:/admin");
        mv.addObject("message", "Successfully created member");

        return mv;
    }


    @RequestMapping(value="/details", method = RequestMethod.GET)
    public ModelAndView instructorDetailView(
            @RequestParam(value="id", required=false, defaultValue="-1") Integer id) {

        ModelAndView err = new ModelAndView("redirect:/admin");
        if (id == -1) return err;
        ModelAndView mv = commonModelAndView("admin/instructors/detail");

        Instructor instructor = userService.findInstructor(id);

        if(instructor == null) return err;

        List<Clazz> clazzes = new ArrayList<>(instructor.getClazzes());
        clazzes.sort(new ClazzDateComparator());

        mv.addObject("clazzes", clazzes);
        mv.addObject("instructors", Arrays.asList(instructor));

        return mv;
    }


}
