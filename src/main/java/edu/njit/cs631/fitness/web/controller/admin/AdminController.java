package edu.njit.cs631.fitness.web.controller.admin;

import edu.njit.cs631.fitness.data.repository.MemberRepository;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import edu.njit.cs631.fitness.service.api.ClazzService;
import edu.njit.cs631.fitness.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController extends BaseController {

    @Override
    protected String getCommonViewName() {
        return "admin";
    }

    @RequestMapping(value="/admin", method= RequestMethod.GET)
    public ModelAndView admin() {
        return commonModelAndView();
    }

}
