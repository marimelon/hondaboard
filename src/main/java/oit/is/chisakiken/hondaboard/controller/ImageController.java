package oit.is.chisakiken.hondaboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import oit.is.chisakiken.hondaboard.service.UserService;

@Controller
public class ImageController {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    UserService userService;

    @GetMapping(value = "/img/users/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody Object getUserProfileImage(@PathVariable Integer id) {
        var image = userService.getUserImage(id);
        if (image.isPresent()) {
            Resource resource = new FileSystemResource(image.get().toString());
            return resource;
        }

        return resourceLoader.getResource("classpath:static/unkown.png");
    }
}
