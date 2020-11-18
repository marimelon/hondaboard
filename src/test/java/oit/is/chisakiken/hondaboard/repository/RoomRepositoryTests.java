package oit.is.chisakiken.hondaboard.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import oit.is.chisakiken.hondaboard.model.Room;

@RunWith(SpringRunner.class)
@MybatisTest
public class RoomRepositoryTests {
    @Autowired
    RoomRepository roomRepository;

    @Test
    public void test1() {
        var newroom = new Room("ルーム名テスト");

        roomRepository.save(newroom);

        var rooms = roomRepository.findAll();

        assertThat(rooms, 
            hasItem(
                hasProperty("name", is("ルーム名テスト"))
        ));
    }
}