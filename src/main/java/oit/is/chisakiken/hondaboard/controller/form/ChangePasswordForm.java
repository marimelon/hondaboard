package oit.is.chisakiken.hondaboard.controller.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordForm {
    @NotBlank
    private String oldpw;

    @NotBlank
    private String newpw;

    private String confirm;

    @AssertTrue(message = "入力が一致していません")
    public boolean isNewPasswordsEqual() {
        return newpw == null ? false : newpw.equals(confirm);
    }
}

