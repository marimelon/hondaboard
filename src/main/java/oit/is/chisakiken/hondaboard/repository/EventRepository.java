package oit.is.chisakiken.hondaboard.repository;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import oit.is.chisakiken.hondaboard.model.Event;

@Mapper
public interface EventRepository {
    @Insert("INSERT INTO event (user_id,title,start_at,end_at) VALUES(#{user_id}, #{title},#{start_at},#{end_at});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Event event);

    @Select("SELECT id,user_id,title,start_at,end_at FROM event;")
    ArrayList<Event> findAll();
}
