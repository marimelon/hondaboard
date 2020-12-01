package oit.is.chisakiken.hondaboard.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oit.is.chisakiken.hondaboard.dto.Message;
import oit.is.chisakiken.hondaboard.model.Comment;
import oit.is.chisakiken.hondaboard.repository.CommentRepository;
import oit.is.chisakiken.hondaboard.repository.LoginUserRepository;

@Service
public class ChatService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    LoginUserRepository loginUserRepository;

    public void postMessage(int userID, int roomID, String message) {
        var comment = new Comment(roomID, userID, message, new Timestamp(System.currentTimeMillis()));
        commentRepository.save(comment);
    }

    public List<Message> getMessage(int roomID) {
        var messages = new ArrayList<Message>();
        for (var m : commentRepository.findByRoomId(roomID)) {
            var user = loginUserRepository.findById(m.getUser_id());

            messages.add(new Message(user.get().getName(), m.getContent()));
        }
        return messages;
    }

}