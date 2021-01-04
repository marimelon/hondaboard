package oit.is.chisakiken.hondaboard.controller.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileImageUploadForm {
    private MultipartFile file;
}
