package oit.is.chisakiken.hondaboard.controller;

import java.security.Principal;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Conventions;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import oit.is.chisakiken.hondaboard.controller.form.RegisterUserForm;
import oit.is.chisakiken.hondaboard.security.AuthConfiguration;
import oit.is.chisakiken.hondaboard.service.UserAccountService;
import oit.is.chisakiken.hondaboard.service.exception.DuplicateUserException;

@Controller
public class RegisterUserController {

    @Autowired
    protected AuthConfiguration authConfiguration;

    @Autowired
    UserAccountService userAccountService;

    @GetMapping("/register")
    public String getRegister(RegisterUserForm registerUserForm, Principal principal, ModelMap model) {
        if (model.containsKey("errors")) {
            var key = BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(registerUserForm);
            model.addAttribute(key, model.get("errors"));
        }

        if (principal != null) {
            // ログイン済み
            return "redirect:/userpage";
        }

        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Validated RegisterUserForm registerUserForm, BindingResult bindingResult,
            RedirectAttributes ra, HttpServletRequest request) throws AuthenticationException, Exception {
        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute(registerUserForm);
            ra.addFlashAttribute("errors", bindingResult);
            return "redirect:/register";
        }

        try {
            userAccountService.registerUser(registerUserForm.getUsername(), registerUserForm.getPassword());
        } catch (DuplicateUserException e) {
            bindingResult.reject(null, "すでに登録されているユーザ名です");

            ra.addFlashAttribute(registerUserForm);
            ra.addFlashAttribute("errors", bindingResult);
            return "redirect:/register";
        }

        // ログイン処理
        var token = new UsernamePasswordAuthenticationToken(registerUserForm.getUsername(),
                registerUserForm.getPassword());

        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        var authenticatedUser = authConfiguration.authenticationManagerBean().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

        return "redirect:/userpage";
    }
}