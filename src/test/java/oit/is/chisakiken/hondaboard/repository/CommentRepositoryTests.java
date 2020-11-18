package oit.is.chisakiken.hondaboard.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import oit.is.chisakiken.hondaboard.model.Comment;

@RunWith(SpringRunner.class)
@MybatisTest
public class CommentRepositoryTests {
    @Autowired
    CommentRepository commentRepository;

    @Test
    public void test1() {
        var newcomment = new Comment(1, 2, "コメント", new Timestamp(System.currentTimeMillis()));

        commentRepository.save(newcomment);

        var comments = commentRepository.findAll();

        assertThat(comments, 
            hasItem(allOf(
                hasProperty("room_id", is(1)),
                hasProperty("user_id", is(2)),
                hasProperty("content", is("コメント"))
        )));
    }

    @Test
    public void test2() {
        var newcomment = new Comment(1, 2, "コメント", new Timestamp(System.currentTimeMillis()));

        commentRepository.save(newcomment);

        var comments = commentRepository.findByRoomId(1);

        assertThat(comments, 
            hasItem(allOf(
                hasProperty("room_id", is(1)),
                hasProperty("user_id", is(2)),
                hasProperty("content", is("コメント"))
        )));
    }

    @Test
    public void test3() {
        var newcomment = new Comment(1, 2, "コメント", new Timestamp(System.currentTimeMillis()));

        commentRepository.save(newcomment);

        var comments = commentRepository.findByUserId(2);

        assertThat(comments, 
            hasItem(allOf(
                hasProperty("room_id", is(1)),
                hasProperty("user_id", is(2)),
                hasProperty("content", is("コメント"))
        )));
    }
}
