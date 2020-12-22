package oit.is.chisakiken.hondaboard.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import oit.is.chisakiken.hondaboard.service.ChatService;

@Controller
public class RootPageController {

    @Autowired
    ChatService chatService;

    @GetMapping("/")
    public String getRoot(Principal principal, ModelMap model) {

        if (principal != null) {
            return "redirect:/userpage";
        }

        int num = 10;
        model.addAttribute("messages", chatService.getLatest(num));
        return "index.html";
    }

    @RequestMapping("login")
    public String getLogin() {
        return "redirect:/";
    }
}
