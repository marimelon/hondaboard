package oit.is.chisakiken.hondaboard.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatPageController {
    @GetMapping("/chatpage")
    public String getChatpage(Principal prin, ModelMap model) {
        String loginUser = prin.getName();
        model.addAttribute("name", loginUser);
        return "chatpage.html";
    }

    @PostMapping("/chatpage")
    public String postChatpage(Principal prin, @RequestParam String send_message, ModelMap model) {
        String loginUser = prin.getName();
        model.addAttribute("name", loginUser);
        String received_message = loginUser + ":" + send_message;
        model.addAttribute("received_message", received_message);
        return "chatpage.html";
    }
}
