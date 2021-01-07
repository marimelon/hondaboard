package oit.is.chisakiken.hondaboard.controller.form;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeNameForm {
    @NotBlank
    private String newusername;
}
