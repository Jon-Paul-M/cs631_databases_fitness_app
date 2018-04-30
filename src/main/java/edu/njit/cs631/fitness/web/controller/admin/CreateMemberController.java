package edu.njit.cs631.fitness.web.controller.admin;

import edu.njit.cs631.fitness.data.entity.Membership;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import edu.njit.cs631.fitness.service.api.UserService;
import edu.njit.cs631.fitness.web.controller.BaseController;
import edu.njit.cs631.fitness.web.model.MemberModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value="/admin/members")
public class CreateMemberController extends BaseController{

    @Override
    protected String getCommonViewName() {
        return "admin/members/create";
    }

    @Override
    protected ModelAndView addParams(ModelAndView modelAndView) {
        return super.addParams(modelAndView);
    }


    @RequestMapping(value="/create", method=RequestMethod.GET)
    public ModelAndView createMember(Model m) {
        ModelAndView mv = commonModelAndView();
        mv.addObject("memberModel", new MemberModel());
        return mv;
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public ModelAndView createMember(@Valid MemberModel memberModel, BindingResult result, ModelAndView mv) {
        if (result.hasErrors()) {
            mv.addObject("memberModel", memberModel);
            addParams(mv);
            return mv;
        }

        userService.registerNewMemberAccount(memberModel);

        return commonModelAndView("redirect:/admin")
                .addObject("message", "Sucessfully created member");
    }

}
