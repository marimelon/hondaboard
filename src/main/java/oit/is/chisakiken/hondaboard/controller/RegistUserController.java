package oit.is.chisakiken.hondaboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.chisakiken.hondaboard.model.LoginUser;
import oit.is.chisakiken.hondaboard.model.LoginUserMapper;

@Controller
public class RegistUserController {

    @GetMapping("/register")
    public String getRegister() {
        return "register.html";
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    LoginUserMapper loginuserMapper;

    @PostMapping("/register")
    @Transactional
    public String postRegister(@RequestParam String username, @RequestParam String password,
            @RequestParam String password2, ModelMap model) {
        if (password.equals(password2)) {
            LoginUser user = new LoginUser();
            user.setName(username);
            user.setPassword(passwordEncoder.encode(password));
            loginuserMapper.insertLoginUser(user);
            return "redirect:/userpage";
        } else {
            return "redirect:/register";
        }
    }
}
