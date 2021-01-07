package oit.is.chisakiken.hondaboard.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import oit.is.chisakiken.hondaboard.model.LoginUser;

@Mapper
public interface LoginUserRepository {

    @Insert("INSERT INTO user (name,password) VALUES(#{name}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(LoginUser user);

    @Update("UPDATE user SET password=#{password} WHERE id=#{id}")
    void updatePassword(@Param("id") int id, @Param("password") String password);

    @Update("UPDATE user SET name=#{name} WHERE id=#{id}")
    void updateName(@Param("id") int id, @Param("name") String name);

    @Select("SELECT id, name, password FROM user WHERE id = #{id}")
    Optional<LoginUser> findById(int id);

    @Select("SELECT id, name, password FROM user WHERE name = #{name}")
    Optional<LoginUser> findByName(String name);
}
