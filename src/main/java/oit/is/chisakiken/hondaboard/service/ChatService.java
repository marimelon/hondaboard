package oit.is.chisakiken.hondaboard.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oit.is.chisakiken.hondaboard.model.Comment;
import oit.is.chisakiken.hondaboard.repository.CommentRepository;

@Service
public class ChatService {

    @Autowired
    CommentRepository commentRepository;

    public void postMessage(int userID, int roomID, String message) {
        var comment = new Comment(roomID, userID, message, new Timestamp(System.currentTimeMillis()));
        commentRepository.save(comment);
    }

}