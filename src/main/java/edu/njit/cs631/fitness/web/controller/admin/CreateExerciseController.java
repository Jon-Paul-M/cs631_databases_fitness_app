package edu.njit.cs631.fitness.web.controller.admin;

import edu.njit.cs631.fitness.data.entity.Exercise;
import edu.njit.cs631.fitness.data.repository.ExerciseRepository;
import edu.njit.cs631.fitness.web.controller.BaseController;
import edu.njit.cs631.fitness.web.model.ExerciseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value="/admin/exercises")
public class CreateExerciseController extends BaseController {

    private final static Logger log = LoggerFactory.getLogger(CreateExerciseController.class);

    @Autowired
    private ExerciseRepository exerciseRepository;

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
        if (result.hasErrors()) {
            mv.addObject("exerciseModel", exerciseModel);
            addParams(mv);
            return mv;
        }

        exerciseService.addNewExercise(exerciseModel);

        return commonModelAndView("redirect:/admin")
                .addObject("message", "Successfully created exercise.");
    }

    @RequestMapping(value="/illustrateDeleteAll", method = RequestMethod.GET)
    @Transactional
    public String illustrateDeleteAll() {
        List<Integer> exerciseList = exerciseService
                .listAllExercises()
                .stream()
                .map(Exercise::getId)
                .collect(Collectors.toList());

        for (Integer id : exerciseList) {
            exerciseService.deleteExercise(id);
        }

        return "redirect:/admin/";
    }


    @RequestMapping(value="/edit", method = RequestMethod.GET)
    public ModelAndView memberEdit(
            @RequestParam(value="id", required=false, defaultValue="-1") Integer id) {

        ModelAndView err = new ModelAndView("redirect:/admin");
        if (id == -1) return err;
        Exercise exercise = exerciseRepository.findOne(id);
        if(exercise == null) return err;

        ModelAndView mv = commonModelAndView();
        ExerciseModel exerciseModel = new ExerciseModel();
        exerciseModel.copyFromExercise(exercise);
        mv.addObject("formAction", "edit");
        mv.addObject("exerciseModel", exerciseModel);
        return mv;

    }

    @RequestMapping(value="/edit", method = RequestMethod.POST)
    public ModelAndView memberEditPost(
            @Valid ExerciseModel exerciseModel, BindingResult result, ModelAndView mv) {

        ModelAndView err = new ModelAndView("redirect:/admin");
        if(exerciseModel.getId() == null) {
            return err;
        }

        Exercise exercise = exerciseRepository.findOne(exerciseModel.getId());
        if(exercise == null) return err;

        if (result.hasErrors()) {
            mv = commonModelAndView();
            mv.addObject("formAction", "edit");
            mv.addObject("exerciseModel", exerciseModel);
            return mv;
        }

        exerciseService.editExercise(exerciseModel);

        return new ModelAndView("redirect:/admin/members/details?id=" + exerciseModel.getId());
    }
}
