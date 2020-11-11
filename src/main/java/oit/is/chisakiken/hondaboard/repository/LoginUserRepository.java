package oit.is.chisakiken.hondaboard.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import oit.is.chisakiken.hondaboard.model.LoginUser;

@Mapper
public interface LoginUserRepository {
    @Select("SELECT id, name, password FROM user WHERE name = #{name}")
    Optional<LoginUser> findByName(String name);
}
