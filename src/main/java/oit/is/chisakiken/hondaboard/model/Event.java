package oit.is.chisakiken.hondaboard.model;

import lombok.Data;

@Data
public class Event {
    int id;
    final int user_id;
    final String title;
    final String start_at;
    final String end_at;
}
