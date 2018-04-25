package edu.njit.cs631.fitness.web.controller;

import edu.njit.cs631.fitness.data.entity.Membership;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private MembershipRepository membershipRepository;

    @RequestMapping(value = {"/", "/home", "/login", "/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String home(Model model) {
        List<Membership> memberships = membershipRepository.findAll();
        model.addAttribute("memberships", memberships );

        return "home";
    }
}
