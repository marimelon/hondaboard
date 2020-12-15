package oit.is.chisakiken.hondaboard.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import oit.is.chisakiken.hondaboard.controller.form.RegisterEventForm;
import oit.is.chisakiken.hondaboard.model.LoginUser;
import oit.is.chisakiken.hondaboard.service.EventService;

@Controller
public class EventController {
    @Autowired
    EventService eventService;

    @PostMapping("/event/register")
    public String register(@Validated RegisterEventForm registerEventForm, BindingResult bindingResult,
            RedirectAttributes ra, Principal prin) {
        Authentication auth = (Authentication) prin;
        LoginUser user = (LoginUser) auth.getPrincipal();
        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute(registerEventForm);
            ra.addFlashAttribute("errors", bindingResult);
            return "redirect:/userpage";
        }
        eventService.registerNewEvent(user.getId(), registerEventForm.getTitle(), registerEventForm.getStart_at(),
                registerEventForm.getEnd_at());
        return "redirect:/userpage";
    }
}
