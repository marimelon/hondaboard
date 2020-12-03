package oit.is.chisakiken.hondaboard.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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

    private MultiValueMap<Integer, SseEmitter> userConnections = new LinkedMultiValueMap<>();

    public void postMessage(int userID, int roomID, String message) {
        var comment = new Comment(roomID, userID, message, new Timestamp(System.currentTimeMillis()));
        commentRepository.save(comment);
        asyncSend(comment);
    }

    public List<Message> getMessage(int roomID) {
        var messages = new ArrayList<Message>();
        for (var m : commentRepository.findByRoomId(roomID)) {
            var user = loginUserRepository.findById(m.getUser_id());

            messages.add(new Message(user.get().getName(), m.getContent()));
        }
        return messages;
    }

    public void joinUser(SseEmitter sseEmitter, int roomID) {
        userConnections.add(roomID, sseEmitter);
        sseEmitter.onTimeout(() -> {
            sseEmitter.complete();
        });
    }

    @Async
    public void asyncSend(Comment comment) {
        var closedEmitters = new ArrayList<SseEmitter>();
        Message message = new Message(loginUserRepository.findById(comment.getUser_id()).get().getName(),
                comment.getContent());

        var mapper = new ObjectMapper();
        try {
            var event = SseEmitter.event().data(mapper.writeValueAsString(message)).reconnectTime(10_000L);

            for (var emitter : userConnections.get(comment.getRoom_id())) {
                try {
                    emitter.send(event);
                } catch (Exception e) {
                    closedEmitters.add(emitter);
                }
            }
            for (var emitter : closedEmitters) {
                userConnections.get(comment.getRoom_id()).remove(emitter);
            }
        } catch (JsonProcessingException e) {
            System.out.println("Exception:" + e.getClass().getName() + ":" + e.getMessage());
            return;
        }
    }

}