package oit.is.chisakiken.hondaboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oit.is.chisakiken.hondaboard.model.Room;
import oit.is.chisakiken.hondaboard.repository.RoomRepository;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

    public void registerNewRoom(String name){
        var room = new Room(name);
        roomRepository.save(room);
        return;
    }
}
