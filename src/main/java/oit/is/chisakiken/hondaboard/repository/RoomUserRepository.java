package oit.is.chisakiken.hondaboard.repository;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import oit.is.chisakiken.hondaboard.model.RoomUser;

@Mapper
public interface RoomUserRepository {

    @Insert("INSERT INTO roomuser (room_id, user_id, role) VALUES(#{room_id}, #{user_id}, #{role});")
    void save(RoomUser room_user);

    @Select("SELECT room_id, user_id, role FROM roomuser;")
    ArrayList<RoomUser> findAll();

    @Select("SELECT room_id, user_id, role FROM roomuser WHERE room_id=#{room_id};")
    ArrayList<RoomUser> findByRoom(int room_id);

    @Select("SELECT room_id, user_id, role FROM roomuser WHERE user_id=#{user_id};")
    ArrayList<RoomUser> findByUser(int user_id);

}
