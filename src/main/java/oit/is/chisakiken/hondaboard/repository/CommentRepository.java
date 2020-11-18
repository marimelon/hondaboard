package oit.is.chisakiken.hondaboard.repository;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import oit.is.chisakiken.hondaboard.model.Comment;

@Mapper
public interface CommentRepository {
    @Insert("INSERT INTO comment (room_id, user_id, content, date) VALUES (#{room_id}, #{user_id}, #{content}, #{date});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Comment comment);

    @Select("SELECT id, room_id, user_id, date, content FROM comment;")
    ArrayList<Comment> findAll();

    @Select("SELECT id, room_id, user_id, date, content FROM comment WHERE room_id=#{room_id};")
    ArrayList<Comment> findByRoomId(int room_id);

    @Select("SELECT id, room_id, user_id, date, content FROM comment WHERE user_id=#{user_id};")
    ArrayList<Comment> findByUserId(int room_id);
}
