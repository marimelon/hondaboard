package oit.is.chisakiken.hondaboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import oit.is.chisakiken.hondaboard.model.Comment;
import oit.is.chisakiken.hondaboard.model.LoginUser;

@Data
@AllArgsConstructor
public class Message {
    int id;
    String username;
    String content;

    public Message(LoginUser user,Comment comment){
        this.id = comment.getId();
        this.username = user.getName();
        this.content = comment.getContent();
    }
}
