package edu.njit.cs631.fitness.web.controller.admin;

import edu.njit.cs631.fitness.data.entity.InstructorTypes;
import edu.njit.cs631.fitness.service.api.UserService;
import edu.njit.cs631.fitness.web.model.InstructorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value="/admin/instructors")
public class CreateInstructorController {

    @Autowired
    private UserService userService;


    @RequestMapping(value="/create", method= RequestMethod.GET)
    public String createInstructor(Model m) {
        m.addAttribute("instructorTypes", InstructorTypes.values() );
        m.addAttribute("instructorModel", new InstructorModel());

        return "admin/instructors/create";
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String createInstructor(@Valid InstructorModel instructorModel, BindingResult result, Model m) {
        if (result.hasErrors()) {
            m.addAttribute("InstructorTypes", InstructorTypes.values() );
            return "admin/instructors/create";
        }

        userService.registerNewInstructorAccount(instructorModel);

        m.addAttribute("message", "Successfully created member");
        return "redirect:/admin";
    }


}
