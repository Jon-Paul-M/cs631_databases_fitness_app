package edu.njit.cs631.fitness.web.controller.admin;

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.entity.Instructor;
import edu.njit.cs631.fitness.data.entity.Member;
import edu.njit.cs631.fitness.data.entity.Membership;
import edu.njit.cs631.fitness.data.repository.MemberRepository;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import edu.njit.cs631.fitness.service.api.ClazzService;
import edu.njit.cs631.fitness.service.api.InstructorService;
import edu.njit.cs631.fitness.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController extends BaseController {

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private InstructorService instructorService;

    @Override
    protected String getCommonViewName() {
        return "admin";
    }

    @RequestMapping(value="/admin", method= RequestMethod.GET)
    public ModelAndView admin() {
        return commonModelAndView();
    }

}
