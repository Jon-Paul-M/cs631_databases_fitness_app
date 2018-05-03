package edu.njit.cs631.fitness.web.controller.admin;

import edu.njit.cs631.fitness.service.api.MembershipService;
import edu.njit.cs631.fitness.web.controller.BaseController;
import edu.njit.cs631.fitness.web.model.ExerciseModel;
import edu.njit.cs631.fitness.web.model.MembershipModel;
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

import javax.validation.Valid;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value="/admin/memberships")
public class CreateMembershipController extends BaseController {

    private final static Logger log = LoggerFactory.getLogger(CreateMembershipController.class);


    @Autowired
    private MembershipService membershipService;

    @Override
    protected String getCommonViewName() {
        return "admin/memberships/create";
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
            //membershipService.deleteExercise(id);
        }

        return "redirect:/admin";
    }

    @RequestMapping(value="/create", method= RequestMethod.GET)
    public ModelAndView createExercise(Model m) {
        ModelAndView mv = commonModelAndView();
        mv.addObject("membershipModel", new MembershipModel());
        return mv;
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public ModelAndView createExercise(@Valid MembershipModel membershipModel, BindingResult result, ModelAndView mv) {
        log.info(membershipModel.toString());
        if (result.hasErrors()) {
            mv.addObject("membershipModel", membershipModel);
            addParams(mv);
            return mv;
        }

        membershipService.addNewMembership(membershipModel);
        log.info("Added membership " + membershipModel);

        return commonModelAndView("redirect:/admin")
                .addObject("message", "Successfully created membership.");
    }
}
