package oit.is.chisakiken.hondaboard.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.allOf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import oit.is.chisakiken.hondaboard.model.RoomUser;

@RunWith(SpringRunner.class)
@MybatisTest
public class RoomUserRepositoryTests {
    @Autowired
    RoomUserRepository roomUserRepository;

    @Test
    public void test1() {
        var newroomuser = new RoomUser(1,2,"admin");

        roomUserRepository.save(newroomuser);

        var roomusers = roomUserRepository.findAll();

        assertThat(roomusers, 
            hasItem(allOf(
                hasProperty("room_id", is(1)),
                hasProperty("user_id", is(2)),
                hasProperty("role", is("admin"))
        )));
    }
}