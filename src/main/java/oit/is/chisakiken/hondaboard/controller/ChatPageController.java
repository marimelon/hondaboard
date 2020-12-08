package oit.is.chisakiken.hondaboard.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import oit.is.chisakiken.hondaboard.model.LoginUser;
import oit.is.chisakiken.hondaboard.service.ChatService;

@Controller
public class ChatPageController {

    @Autowired
    ChatService chatService;

    @GetMapping("/chatpage")
    public String getChatpage(@RequestParam Integer id, Principal prin, ModelMap model) {
        model.addAttribute("id", id);
        String loginUser = prin.getName();
        model.addAttribute("name", loginUser);
        var messages = chatService.getMessage(id);
        model.addAttribute("messages", messages);
        return "chatpage.html";
    }

    @PostMapping("/chatpage")
    public String postChatpage(@RequestParam Integer id, RedirectAttributes redirectAttributes, Principal prin,
            @RequestParam String send_message, ModelMap model) {
        Authentication auth = (Authentication) prin;
        LoginUser user = (LoginUser) auth.getPrincipal();

        chatService.postMessage(user.getId(), id, send_message);

        return "redirect:chatpage?id="+id;
    }

    @GetMapping("/chatpage/sse")
    public SseEmitter asyncChat(@RequestParam Integer id) {
        SseEmitter sseEmitter = new SseEmitter();
        chatService.joinUser(sseEmitter, id);
        return sseEmitter;
    }

}
