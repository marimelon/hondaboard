package oit.is.chisakiken.hondaboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventDto {
    private String title;
    private String start;
    private String end;
}
