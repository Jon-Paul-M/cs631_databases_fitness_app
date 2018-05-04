package edu.njit.cs631.fitness.web.controller.admin;

import javax.validation.Valid;

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.entity.Member;
import edu.njit.cs631.fitness.web.error.UserAlreadyExistException;
import edu.njit.cs631.fitness.web.error.UserNotFoundException;
import edu.njit.cs631.fitness.web.util.ClazzDateComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.cs631.fitness.web.controller.BaseController;
import edu.njit.cs631.fitness.web.model.MemberModel;

import java.util.ArrayList;
import java.util.Arrays;
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


    @RequestMapping(value = {"/generate"}, method = RequestMethod.GET)
    public String generate() {

        if (hasAuthority("ADMIN")) {
            userService.generateManyMembers();
        }

        return "redirect:/admin";
    }

    @RequestMapping(value="/details", method = RequestMethod.GET)
    public ModelAndView memberDetailView(
            @RequestParam(value="id", required=false, defaultValue="-1") Integer id) {

        ModelAndView err = new ModelAndView("redirect:/admin");
        if (id == -1) return err;
        ModelAndView mv = commonModelAndView("admin/members/detail");

        Member member = memberRepository.findOne(id);

        if(member == null) return err;

        List<Clazz> clazzes = new ArrayList<>(member.getClazzes());
        clazzes.sort(new ClazzDateComparator());
        mv.addObject("clazzes", clazzes);
        mv.addObject("members", Arrays.asList(member));

        return mv;
    }

    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public String deleteGet(
            @RequestParam(value="id", required=false, defaultValue="-1") Integer id) {

        if (id == -1) return "redirect:/admin";

        userService.deleteMemberAccount(id); // let the error template render it

        return "redirect:/admin";
    }

    @RequestMapping(value="/edit", method = RequestMethod.GET)
    public ModelAndView memberEdit(
            @RequestParam(value="id", required=false, defaultValue="-1") Integer id) {

        ModelAndView err = new ModelAndView("redirect:/admin");
        if (id == -1) return err;
        Member member = memberRepository.findOne(id);
        if(member == null) return err;

        ModelAndView mv = commonModelAndView();
        MemberModel memberModel = new MemberModel();
        memberModel.copyFromMember(member);
        mv.addObject("formAction", "edit");
        mv.addObject("memberModel", memberModel);
        return mv;

    }

    @RequestMapping(value="/edit", method = RequestMethod.POST)
    public ModelAndView memberEditPost(
            @Valid MemberModel memberModel, BindingResult result, ModelAndView mv) {

        ModelAndView err = new ModelAndView("redirect:/admin");
        if(memberModel.getId() == null) {
            return err;
        }

        Member member = memberRepository.findOne(memberModel.getId());
        if(member == null) return err;

        if (result.hasErrors()) {
            mv = commonModelAndView();
            mv.addObject("formAction", "edit");
            mv.addObject("memberModel", memberModel);
            return mv;
        }

        try {
            userService.editMemberAccount(memberModel);
        } catch (UserNotFoundException e) {
            mv = commonModelAndView();
            result.addError(new ObjectError("id", e.getMessage()));
            mv.addObject("formAction", "edit");
            mv.addObject("memberModel", memberModel);
            return mv;
        } catch (UserAlreadyExistException e) {
            mv = commonModelAndView();
            result.addError(new ObjectError("email", e.getMessage()));
            mv.addObject("formAction", "edit");
            mv.addObject("memberModel", memberModel);
            return mv;
        }
        return new ModelAndView("redirect:/admin/members/details?id=" + memberModel.getId());
    }

}
