package oit.is.chisakiken.hondaboard.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Comment {
    int id;
    final int room_id;
    final int user_id;
    final String content;
    final Timestamp date;
}
