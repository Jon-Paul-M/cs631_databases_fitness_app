package edu.njit.cs631.fitness.web.controller.admin;

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.entity.Member;
import edu.njit.cs631.fitness.data.entity.Membership;
import edu.njit.cs631.fitness.data.repository.MemberRepository;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import edu.njit.cs631.fitness.service.api.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private MemberRepository memberRepository;


    @RequestMapping(value="/admin", method= RequestMethod.GET)
    public String admin(Model model) {

        List<Clazz> clazzes = clazzService.listFutureActiveClasses();
        List<Member> members = memberRepository.findAll();
        List<Membership> memberships = membershipRepository.findAll();
        model.addAttribute("memberships", memberships );
        model.addAttribute("members", members);
        model.addAttribute("clazzes", clazzes);

        return "admin";
    }
}
