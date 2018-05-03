package edu.njit.cs631.fitness.web.controller;

import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.data.repository.security.UserRepository;
import edu.njit.cs631.fitness.web.model.PasswordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class HomeController extends BaseController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping(value = {"/", "/home", "/login", "/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView home() {
        return commonModelAndView();
    }

    @Override
    protected String getCommonViewName() {
        return "home";
    }

    @RequestMapping(value="/password", method= RequestMethod.GET)
    public ModelAndView changePassword(Model m) {
        ModelAndView mv = commonModelAndView("changepassword");
        mv.addObject("passwordModel", new PasswordModel());
        return mv;
    }

    @RequestMapping(value="/password", method=RequestMethod.POST)
    public ModelAndView changePassword(@Valid PasswordModel passwordModel, BindingResult result, ModelAndView mv) {
        mv.addObject("passwordModel", passwordModel);
        mv.setViewName("changepassword");
        addParams(mv);
        if (result.hasErrors()) {
            return mv;
        }

        User user = getCurrentUser();
        if (user == null) return commonModelAndView("redirect:/");
        if(!passwordEncoder.matches(passwordModel.getOldPassword(), user.getPasswordHash())) {
            result.addError(new ObjectError("Old password", "Old password does not match"));
            return mv;
        }

        if(!passwordModel.getNewPassword().equals(passwordModel.getConfirmNewPassword())) {
            result.addError(new ObjectError("Password", "New password and confirmation do not match."));
            return mv;
        }

        user.setPasswordHash(passwordEncoder.encode(passwordModel.getNewPassword()));
        userRepository.saveAndFlush(user);

        return commonModelAndView("redirect:/")
                .addObject("message", "Sucessfully changed password");
    }
}
