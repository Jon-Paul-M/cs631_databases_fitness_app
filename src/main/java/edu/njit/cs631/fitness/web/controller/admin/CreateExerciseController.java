package edu.njit.cs631.fitness.web.controller.admin;

import edu.njit.cs631.fitness.web.controller.BaseController;
import edu.njit.cs631.fitness.web.model.ExerciseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value="/admin/exercises")
public class CreateExerciseController extends BaseController {

    private final static Logger log = LoggerFactory.getLogger(CreateExerciseController.class);

    @Override
    protected String getCommonViewName() {
        return "admin/exercises/create";
    }

    @Override
    protected ModelAndView addParams(ModelAndView modelAndView) {
        return super.addParams(modelAndView);
    }

    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public String deleteGet(
            @RequestParam(value="id", required=false, defaultValue="-1") Integer id) {

        if (id == -1) return "redirect:/admin";

        if (hasAuthority("ADMIN")) {
            exerciseService.deleteExercise(id);
        }

        return "redirect:/admin";
    }

    @RequestMapping(value="/create", method= RequestMethod.GET)
    public ModelAndView createExercise(Model m) {
        ModelAndView mv = commonModelAndView();
        mv.addObject("exerciseModel", new ExerciseModel());
        return mv;
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public ModelAndView createExercise(@Valid ExerciseModel exerciseModel, BindingResult result, ModelAndView mv) {
        log.error(exerciseModel.toString());
        if (result.hasErrors()) {
            mv.addObject("exerciseModel", exerciseModel);
            addParams(mv);
            return mv;
        }

        exerciseService.addNewExercise(exerciseModel);

        return commonModelAndView("redirect:/admin")
                .addObject("message", "Sucessfully created exercise");
    }
}
