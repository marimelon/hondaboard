package oit.is.chisakiken.hondaboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import oit.is.chisakiken.hondaboard.controller.form.RegisterRoomForm;
import oit.is.chisakiken.hondaboard.service.RoomService;

@Controller
public class RegisterRoomController {
    @Autowired
    RoomService roomService;

    @PostMapping("/room/register")
    public String register(@Validated RegisterRoomForm registerRoomForm, BindingResult bindingResult,
            RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute(registerRoomForm);
            ra.addFlashAttribute("errors", bindingResult);
            return "redirect:/userpage";
        }
        roomService.registerNewRoom(registerRoomForm.getName());
        return "redirect:/userpage";
    }
}
