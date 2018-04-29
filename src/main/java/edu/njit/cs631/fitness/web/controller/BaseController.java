package edu.njit.cs631.fitness.web.controller;


import edu.njit.cs631.fitness.data.repository.MemberRepository;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import edu.njit.cs631.fitness.service.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Controller
public abstract class BaseController {

    private final static Logger log = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected ClazzService clazzService;

    @Autowired
    protected InstructorService instructorService;

    @Autowired
    protected ExerciseService exerciseService;

    @Autowired
    protected RoomService roomService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected MembershipRepository membershipRepository;

    protected abstract String getCommonViewName();

    protected ModelAndView commonModelAndView() {
        return addParams(new ModelAndView(getCommonViewName()));

    }
    protected ModelAndView commonModelAndView(String viewName) {
        return addParams(new ModelAndView(viewName));
    }

    protected ModelAndView commonModelAndView(ModelAndView modelAndView) {
        return modelAndView;
    }

    protected void addInstructors(ModelAndView modelAndView) {
        modelAndView.addObject("instructors", instructorService.listAllInstructors());
    }

    protected void addRooms(ModelAndView modelAndView) {
        modelAndView.addObject("rooms", roomService.listAllRooms());
    }

    protected void addClazzes(ModelAndView modelAndView) {
        modelAndView.addObject("clazzes", clazzService.listFutureActiveClasses());
    }

    protected void addMembers(ModelAndView modelAndView) {
        modelAndView.addObject("members", memberRepository.findAll());
    }

    protected void addExercises(ModelAndView modelAndView) {
        modelAndView.addObject("exercises", exerciseService.listAllExercises());
    }

    protected void addMemberships(ModelAndView modelAndView) {
        modelAndView.addObject("memberships", membershipRepository.findAll());
    }

    protected void addCurrentUser(ModelAndView modelAndView) {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            org.springframework.security.core.userdetails.UserDetails springUser;
            log.info(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            Object userAuth = (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            if ("anonymousUser".equals(userAuth)) {
                modelAndView.addObject("currentUser", null);
            } else {
                springUser = (org.springframework.security.core.userdetails.UserDetails)(userAuth);
                modelAndView.addObject("currentUser", userService.findUserByEmail(springUser.getUsername()));
            }
        } else {
            modelAndView.addObject("currentUser", null);
        }
    }


    protected ModelAndView addParams(ModelAndView modelAndView) {
        addClazzes(modelAndView);
        addCurrentUser(modelAndView);
        addInstructors(modelAndView);
        addMembers(modelAndView);
        addRooms(modelAndView);
        addExercises(modelAndView);
        addMemberships(modelAndView);
        return modelAndView;
    }

}
