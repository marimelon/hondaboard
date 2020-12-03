package oit.is.chisakiken.hondaboard.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootPageController {
    @GetMapping("/")
    public String getRoot(Principal principal) {

        if (principal != null) {
            return "redirect:/userpage";
        }

        return "index.html";
    }
}
