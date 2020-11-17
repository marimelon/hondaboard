package oit.is.chisakiken.hondaboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistUserController {

    @GetMapping("/register")
    public String getRegister() {
        return "register.html";
    }

    @PostMapping("/register")
    public String postRegister() {
        return "redirect:/";
    }
}
