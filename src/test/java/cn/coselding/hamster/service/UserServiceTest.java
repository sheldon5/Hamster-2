package cn.coselding.hamster.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by 宇强 on 2016/10/4 0004.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class UserServiceTest {

    @Test
    public void testLogin() throws Exception {

    }

    @Test
    public void testRegister() throws Exception {

    }

    @Test
    public void testDeleteUser() throws Exception {

    }

    @Test
    public void testUpdateUser() throws Exception {

    }

    @Test
    public void testQueryUser() throws Exception {

    }

    @Test
    public void testQueryPageUsers() throws Exception {

    }
}