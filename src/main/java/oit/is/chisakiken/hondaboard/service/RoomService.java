package oit.is.chisakiken.hondaboard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oit.is.chisakiken.hondaboard.model.Room;
import oit.is.chisakiken.hondaboard.model.RoomUser;
import oit.is.chisakiken.hondaboard.repository.RoomRepository;
import oit.is.chisakiken.hondaboard.repository.RoomUserRepository;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomUserRepository roomUserRepository;

    public void registerNewRoom(int userid, String name) {
        var room = new Room(name);
        roomRepository.save(room);
        roomUserRepository.save(new RoomUser(room.getId(), userid, "owner"));
        return;
    }

    public Optional<Room> getRoom(int id){
        return roomRepository.findById(id);
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }
}
