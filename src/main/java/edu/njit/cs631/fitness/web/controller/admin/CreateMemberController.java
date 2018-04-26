package edu.njit.cs631.fitness.web.controller.admin;

import edu.njit.cs631.fitness.data.entity.Membership;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import edu.njit.cs631.fitness.web.model.MemberModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value="/admin/members")
public class CreateMemberController {

    @Autowired
    private MembershipRepository membershipRepository;


    @RequestMapping(value="/create", method=RequestMethod.GET)
    public String createMember(Model m) {
        List<Membership> memberships = membershipRepository.findAll();
        m.addAttribute("membershipTypes", memberships );
        m.addAttribute("memberModel", new MemberModel());

        return "admin/members/create";
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String createMember(@Valid MemberModel memberModel, BindingResult result, Model m) {
        if (result.hasErrors()) {
            List<Membership> memberships = membershipRepository.findAll();
            m.addAttribute("membershipTypes", memberships);
            return "admin/members/create";
        }
        m.addAttribute("message", "Successfully created member");
        return "redirect:/admin";
    }


}
