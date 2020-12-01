package oit.is.chisakiken.hondaboard.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.chisakiken.hondaboard.model.LoginUser;
import oit.is.chisakiken.hondaboard.service.ChatService;

@Controller
public class ChatPageController {

    @Autowired
    ChatService chatService;

    @GetMapping("/chatpage")
    public String getChatpage(Principal prin, ModelMap model) {
        String loginUser = prin.getName();
        model.addAttribute("name", loginUser);
        var messages = chatService.getMessage(1);
        model.addAttribute("messages", messages);
        return "chatpage.html";
    }

    @PostMapping("/chatpage")
    public String postChatpage(Principal prin, @RequestParam String send_message, ModelMap model) {
        Authentication auth = (Authentication) prin;
        LoginUser user = (LoginUser) auth.getPrincipal();

        // DBにメッセージを格納.

        chatService.postMessage(user.getId(), 1, send_message);
        return "redirect:chatpage";
    }
}
