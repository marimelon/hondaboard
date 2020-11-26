package oit.is.chisakiken.hondaboard.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginUserMapper {
    @Insert("INSERT INTO user (name,password) VALUES (#{name},#{password})")
    void insertLoginUser(LoginUser loginUser);
}