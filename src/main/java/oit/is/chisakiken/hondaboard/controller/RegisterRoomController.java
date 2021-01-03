package oit.is.chisakiken.hondaboard.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import oit.is.chisakiken.hondaboard.controller.form.RegisterRoomForm;
import oit.is.chisakiken.hondaboard.model.LoginUser;
import oit.is.chisakiken.hondaboard.service.RoomService;

@Controller
public class RegisterRoomController {
    @Autowired
    RoomService roomService;

    @PostMapping("/room/register")
    public String register(@Validated RegisterRoomForm registerRoomForm, BindingResult bindingResult,
            RedirectAttributes ra, Principal prin) {
        Authentication auth = (Authentication) prin;
        LoginUser user = (LoginUser) auth.getPrincipal();
        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute(registerRoomForm);
            ra.addFlashAttribute("errors", bindingResult);
            return "redirect:/userpage";
        }

        if (registerRoomForm.getName().equals("511jp")) {
            return "scp511jp.html";
        }

        roomService.registerNewRoom(user.getId(), registerRoomForm.getName());
        return "redirect:/userpage";
    }
}
