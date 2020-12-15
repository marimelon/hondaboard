package oit.is.chisakiken.hondaboard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oit.is.chisakiken.hondaboard.dto.EventDto;
import oit.is.chisakiken.hondaboard.model.Event;
import oit.is.chisakiken.hondaboard.repository.EventRepository;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;

    public void registerNewEvent(int userid, String title, String start_at, String end_at) {
        var event = new Event(userid, title, start_at, end_at);
        eventRepository.save(event);
        return;
    }

    public List<EventDto> getEvents() {
        var events = new ArrayList<EventDto>();
        for (var e : eventRepository.findAll()) {
            events.add(new EventDto(e.getTitle(), e.getStart_at(),e.getEnd_at()));
        }
        return events;
    }
}
