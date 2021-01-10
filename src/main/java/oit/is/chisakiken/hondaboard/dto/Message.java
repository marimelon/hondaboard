package oit.is.chisakiken.hondaboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import oit.is.chisakiken.hondaboard.model.Comment;
import oit.is.chisakiken.hondaboard.model.LoginUser;
import oit.is.chisakiken.hondaboard.model.Room;

@Data
@AllArgsConstructor
public class Message {
    int id;
    String room_name;
    int userid;
    String username;
    String content;

    public Message(LoginUser user, Comment comment) {
        this.id = comment.getId();
        this.userid = user.getId();
        this.username = user.getName();
        this.content = comment.getContent();
    }

    public Message(LoginUser user, Comment comment, Room room) {
        this.id = comment.getId();
        this.room_name = room.getName();
        this.userid = user.getId();
        this.username = user.getName();
        this.content = comment.getContent();
    }

}
