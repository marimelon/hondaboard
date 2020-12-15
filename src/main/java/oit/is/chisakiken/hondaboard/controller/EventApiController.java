package oit.is.chisakiken.hondaboard.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import oit.is.chisakiken.hondaboard.service.EventService;

@RestController
public class EventApiController {
    @Autowired
    EventService eventService;

    @GetMapping("/event/all")
    public String getEventData() {
        String jsonMsg = null;
        try {
            var events = eventService.getEvents();

            // FullCalendarにエンコード済み文字列を渡す
            ObjectMapper mapper = new ObjectMapper();
            jsonMsg = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
        return jsonMsg;
    }
}
