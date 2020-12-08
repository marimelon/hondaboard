package oit.is.chisakiken.hondaboard.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import oit.is.chisakiken.hondaboard.controller.form.RegisterRoomForm;

@Controller
public class UserPageController {
    @GetMapping("/userpage")
    public String UserPage(RegisterRoomForm registerRoomForm,Principal prin, ModelMap model) {
        String loginUser = prin.getName();
        model.addAttribute("name", loginUser);
        return "userpage.html";
    }
}
