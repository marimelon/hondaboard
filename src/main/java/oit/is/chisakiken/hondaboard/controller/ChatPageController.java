package oit.is.chisakiken.hondaboard.controller;

import java.security.Principal;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import oit.is.chisakiken.hondaboard.dto.Message;
import oit.is.chisakiken.hondaboard.model.LoginUser;
import oit.is.chisakiken.hondaboard.service.ChatService;
import oit.is.chisakiken.hondaboard.service.RoomService;

@Controller
public class ChatPageController {

    @Autowired
    RoomService roomService;

    @Autowired
    ChatService chatService;

    @GetMapping("/chatpage")
    public String getChatpage(@RequestParam Integer id, Principal prin, ModelMap model) {
        model.addAttribute("id", id);
        if (prin != null) {
            String loginUser = prin.getName();
            model.addAttribute("name", loginUser);
        }

        var room = roomService.getRoom(id).get();
        model.addAttribute("roomname", room.getName());
        var messages = chatService.getMessage(id);
        model.addAttribute("messages", messages);
        var maxMessageId = messages.stream().map(Message::getId).max(Comparator.naturalOrder()).orElse(0);
        model.addAttribute("initmessageid", maxMessageId);
        return "chatpage.html";
    }

    @PostMapping("/chatpage")
    public String postChatpage(@RequestParam Integer id, RedirectAttributes redirectAttributes, Principal prin,
            @RequestParam String send_message, ModelMap model) {
        Authentication auth = (Authentication) prin;
        LoginUser user = (LoginUser) auth.getPrincipal();

        chatService.postMessage(user.getId(), id, send_message);

        return "redirect:chatpage?id=" + id;
    }

    @GetMapping("/chatpage/sse")
    public SseEmitter asyncChat(@RequestParam Integer id, @RequestParam(required = false) Integer initmessageid,
            @RequestHeader(name = "Last-Event-ID", required = false) String lastEventId) {
        SseEmitter sseEmitter = new SseEmitter();
        if (lastEventId != null) {
            // 再接続時
            chatService.joinUser(sseEmitter, id, Integer.parseInt(lastEventId));
        } else if (initmessageid != null) {
            // 新規コネクション
            chatService.joinUser(sseEmitter, id, initmessageid);
        } else {
            chatService.joinUser(sseEmitter, id);
        }
        return sseEmitter;
    }

}
