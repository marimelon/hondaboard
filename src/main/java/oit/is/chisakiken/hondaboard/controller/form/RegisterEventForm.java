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
    private String start_at;
    private String end_at;
}
