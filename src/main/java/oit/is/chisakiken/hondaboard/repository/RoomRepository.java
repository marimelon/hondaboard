package oit.is.chisakiken.hondaboard.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import oit.is.chisakiken.hondaboard.model.Room;

@Mapper
public interface RoomRepository {
    @Insert("INSERT INTO room (name) VALUES (#{name});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Room comment);

    @Select("SELECT id, name FROM room;")
    ArrayList<Room> findAll();

    @Select("SELECT id, name FROM room WHERE id=#{id};")
    Optional<Room> findById(int id);
}
