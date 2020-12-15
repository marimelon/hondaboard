package oit.is.chisakiken.hondaboard.controller.form;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegisterEventForm {
    @NotBlank
    private String title;
    @NotBlank
    private String start_at;
    @NotBlank
    private String end_at;
}
