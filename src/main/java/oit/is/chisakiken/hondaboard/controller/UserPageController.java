package oit.is.chisakiken.hondaboard.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import oit.is.chisakiken.hondaboard.controller.form.RegisterEventForm;
import oit.is.chisakiken.hondaboard.controller.form.RegisterRoomForm;
import oit.is.chisakiken.hondaboard.service.RoomService;

@Controller
public class UserPageController {

    @Autowired
    RoomService roomService;

    @GetMapping("/userpage")
    public String UserPage(RegisterRoomForm registerRoomForm, RegisterEventForm registerEventForm, Principal prin,
            ModelMap model) {
        String loginUser = prin.getName();
        model.addAttribute("name", loginUser);
        model.addAttribute("rooms", roomService.getRooms());
        return "userpage.html";
    }
}
