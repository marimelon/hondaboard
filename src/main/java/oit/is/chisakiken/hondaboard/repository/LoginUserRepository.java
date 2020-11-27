package oit.is.chisakiken.hondaboard.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import oit.is.chisakiken.hondaboard.model.LoginUser;

@Mapper
public interface LoginUserRepository {

    @Insert("INSERT INTO user (name,password) VALUES(#{name}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(LoginUser user);

    @Select("SELECT id, name, password FROM user WHERE name = #{name}")
    Optional<LoginUser> findByName(String name);
}
