package oit.is.chisakiken.hondaboard.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootPageController {
    @GetMapping("/")
    public String getRoot(Principal principal) {

        if (principal != null) {
            return "redirect:/userpage";
        }

        return "index.html";
    }

    @RequestMapping("login")
    public String getLogin() {
        return "redirect:/";
    }
}
