package oit.is.chisakiken.hondaboard.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Conventions;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import oit.is.chisakiken.hondaboard.controller.form.ChangeNameForm;
import oit.is.chisakiken.hondaboard.controller.form.ChangePasswordForm;
import oit.is.chisakiken.hondaboard.controller.form.ProfileImageUploadForm;
import oit.is.chisakiken.hondaboard.model.LoginUser;
//import oit.is.chisakiken.hondaboard.repository.LoginUserRepository;
import oit.is.chisakiken.hondaboard.service.UserAccountService;
import oit.is.chisakiken.hondaboard.service.UserService;
import oit.is.chisakiken.hondaboard.service.exception.DifferentOldPasswd;
import oit.is.chisakiken.hondaboard.service.exception.DuplicateUserException;
import oit.is.chisakiken.hondaboard.service.exception.NotFoundUserException;

//import org.springframework.security.core.AuthenticationException;
//import oit.is.chisakiken.hondaboard.security.AuthConfiguration;

@Controller
public class UserProfileController {
    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    UserService userService;

    @Autowired
    UserAccountService userAccountService;

    /*
     * @Autowired LoginUserRepository loginUserRepository;
     * 
     * @Autowired protected AuthConfiguration authConfiguration;
     */

    @GetMapping("/profile")
    public String UserProfile(Principal prin, ModelMap model) {
        String loginUser = prin.getName();
        model.addAttribute("name", loginUser);

        return "userprofile";
    }

    @PostMapping("/profile/upload")
    public String UploadProfileImage(ProfileImageUploadForm form, Principal prin, ModelMap model) throws IOException {
        Authentication auth = (Authentication) prin;
        var user = (LoginUser) auth.getPrincipal();

        userService.updateImage(user.getId(), form.getFile().getBytes());

        return "redirect:/profile";
    }

    @GetMapping(value = "/profile/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody Object GetProfileImage(Principal prin) {
        Authentication auth = (Authentication) prin;
        var user = (LoginUser) auth.getPrincipal();

        var image = userService.getUserImage(user.getId());
        if (image.isPresent()) {
            Resource resource = new FileSystemResource(image.get().toString());
            return resource;
        }

        return resourceLoader.getResource("classpath:static/unkown.png");
    }

    @GetMapping("/profile/changepw")
    public String GetChangePassWord(ChangePasswordForm changePasswordForm, Principal prin, ModelMap model) {
        String loginUser = prin.getName();
        model.addAttribute("name", loginUser);

        if (model.containsKey("errors")) {
            var key = BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(changePasswordForm);
            model.addAttribute(key, model.get("errors"));
        }

        return "changepasswd";
    }

    @PostMapping("/profile/changepw")
    public String postRegister(@Validated ChangePasswordForm changePasswordForm, BindingResult bindingResult,
            RedirectAttributes ra, Principal prin) throws NotFoundUserException {
        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute(changePasswordForm);
            ra.addFlashAttribute("errors", bindingResult);
            return "redirect:/profile/changepw";
        }

        Authentication auth = (Authentication) prin;
        var user = (LoginUser) auth.getPrincipal();

        try {
            userAccountService.changePasswd(user.getId(), changePasswordForm.getOldpw(), changePasswordForm.getNewpw());
        } catch (DifferentOldPasswd e) {
            var fieldError = new FieldError(bindingResult.getObjectName(), "oldpw", "パスワードが間違っています");

            ra.addFlashAttribute(changePasswordForm);
            bindingResult.addError(fieldError);
            ra.addFlashAttribute("errors", bindingResult);
            return "redirect:/profile/changepw";
        }

        ra.addFlashAttribute("system_msg", "<span style=\"color:#28a745;\">パスワードを更新しました</span>");
        return "redirect:/profile";
    }

    @GetMapping("/profile/changename")
    public String GetChangeName(ChangeNameForm changeNameForm, Principal prin, ModelMap model) {
        String loginUser = prin.getName();
        model.addAttribute("name", loginUser);

        if (model.containsKey("errors")) {
            var key = BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(changeNameForm);
            model.addAttribute(key, model.get("errors"));
        }

        return "changename";
    }

    // AuthenticationException, Exception
    @PostMapping("/profile/changename")
    public String postRegisterName(@Validated ChangeNameForm changeNameForm, BindingResult bindingResult,
            RedirectAttributes ra, Principal prin) throws NotFoundUserException {
        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute(changeNameForm);
            ra.addFlashAttribute("errors", bindingResult);
            return "redirect:/profile/changename";
        }

        Authentication auth = (Authentication) prin;
        var user = (LoginUser) auth.getPrincipal();

        try {
            userAccountService.changeName(user.getId(), changeNameForm.getNewusername());
        } catch (DuplicateUserException e) {
            bindingResult.reject(null, "すでに登録されているユーザ名です");

            ra.addFlashAttribute(changeNameForm);
            ra.addFlashAttribute("errors", bindingResult);
            return "redirect:/profile/changename";
        }

        /*
         * var token = new
         * UsernamePasswordAuthenticationToken(changeNameForm.getNewusername(),
         * loginUserRepository.findByName(changeNameForm.getNewusername()).get().
         * getPassword()); request.getSession();
         * 
         * token.setDetails(new WebAuthenticationDetails(request)); var
         * authenticatedUser =
         * authConfiguration.authenticationManagerBean().authenticate(token);
         * SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
         */

        // ra.addFlashAttribute("system_msg", "<span
        // style=\"color:#28a745;\">ユーザー名を更新しました</span>");

        return "redirect:/logout";
    }
}
