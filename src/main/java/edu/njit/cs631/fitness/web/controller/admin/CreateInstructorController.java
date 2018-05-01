package edu.njit.cs631.fitness.web.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.cs631.fitness.data.entity.InstructorTypes;
import edu.njit.cs631.fitness.service.api.UserService;
import edu.njit.cs631.fitness.web.controller.BaseController;
import edu.njit.cs631.fitness.web.model.InstructorModel;

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


}
