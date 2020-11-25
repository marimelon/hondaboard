package oit.is.chisakiken.hondaboard.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {
    @GetMapping("/userpage")
    public String UserPage(Principal prin, ModelMap model) {
        String loginUser = prin.getName();
        model.addAttribute("name", loginUser);
        return "userpage.html";
    }
}
