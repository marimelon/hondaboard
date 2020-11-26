package oit.is.chisakiken.hondaboard.controller.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegisterUserForm {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String confirm;

    @AssertTrue(message = "入力が一致していません")
    public boolean isPasswordsEqual() {
        return password == null ? false : password.equals(confirm);
    }
}
