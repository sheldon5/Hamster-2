package cn.coselding.hamster.dao;

import cn.coselding.hamster.domain.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by 宇强 on 2016/10/4 0004.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Test
    public void testSaveComment() throws Exception {
        Comment comment = new Comment();
        comment.setArtid(110);
        comment.setComcontent("文章不错");
        comment.setComtime(new Date());
        comment.setGemail("111@11.com");
        comment.setGid(1);
        //commentDao.saveComment(comment);
    }

    @Test
    public void testDeleteComnent() throws Exception {
        //commentDao.deleteComnent(11);
    }

    @Test
    public void testUpdateComment() throws Exception {

    }

    @Test
    public void testQueryComment() throws Exception {

    }

    @Test
    public void testQueryCount() throws Exception {

    }

    @Test
    public void testGetPageData() throws Exception {

    }

    @Test
    public void testQueryGuestCount() throws Exception {

    }

    @Test
    public void testGetGuestPageData() throws Exception {

    }
}