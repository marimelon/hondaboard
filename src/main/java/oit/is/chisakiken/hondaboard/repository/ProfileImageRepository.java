package oit.is.chisakiken.hondaboard.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProfileImageRepository {
    @Update("UPDATE user SET image=#{image} WHERE id=#{id}")
    void save(@Param("id") int id, @Param("image") String image);

    @Select("SELECT image FROM user WHERE id = #{id}")
    Optional<String> findByUserId(int id);
}
