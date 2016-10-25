package cn.coselding.hamster.dao;

import cn.coselding.hamster.domain.User;
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
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testSaveUser() throws Exception {
        User user = new User();
        user.setPassword("123");
        user.setUname("Coselding111");
        user.setUtime(new Date());
        //userDao.saveUser(user);
    }

    @Test
    public void testDeleteUser() throws Exception {
        //userDao.deleteUser(2);
    }

    @Test
    public void testUpdateUser() throws Exception {

    }

    @Test
    public void testQueryUser() throws Exception {

    }

    @Test
    public void testQueryUserByName() throws Exception {

    }

    @Test
    public void testGetCount() throws Exception {

    }

    @Test
    public void testQueryUsers() throws Exception {

    }

    @Test
    public void testGetPageData() throws Exception {

    }
}