package edu.njit.cs631.fitness.web.controller.admin;

import edu.njit.cs631.fitness.web.controller.BaseController;
import edu.njit.cs631.fitness.web.model.RoomModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value="/admin/rooms")
public class CreateRoomController extends BaseController {


    @Override
    protected String getCommonViewName() {
        return "admin/rooms/create";
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
            roomService.deleteRoom(id);
        }

        return "redirect:/admin";
    }

    @RequestMapping(value="/create", method= RequestMethod.GET)
    public ModelAndView createRoom(Model m) {
        ModelAndView mv = commonModelAndView();
        mv.addObject("roomModel", new RoomModel());
        return mv;
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public ModelAndView createRoom(@Valid RoomModel roomModel, BindingResult result, ModelAndView mv) {
        if (result.hasErrors()) {
            mv.addObject("roomModel", roomModel);
            addParams(mv);
            return mv;
        }

        roomService.addNewRoom(roomModel);

        return commonModelAndView("redirect:/admin")
                .addObject("message", "Sucessfully created room");
    }
}
