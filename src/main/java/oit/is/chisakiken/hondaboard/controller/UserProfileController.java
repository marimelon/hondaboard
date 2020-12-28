package oit.is.chisakiken.hondaboard.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import oit.is.chisakiken.hondaboard.controller.form.ProfileImageUploadForm;
import oit.is.chisakiken.hondaboard.model.LoginUser;
import oit.is.chisakiken.hondaboard.service.UserService;

@Controller
public class UserProfileController {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    UserService userService;

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
}
