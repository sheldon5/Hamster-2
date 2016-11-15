package cn.coselding.hamster.dao;

import cn.coselding.hamster.domain.Guest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by 宇强 on 2016/10/4 0004.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class GuestDaoTest {

    @Autowired
    private GuestDao guestDao;

    @Ignore
    @Test
    public void testSaveGuest() throws Exception {
        Guest guest = new Guest();
        guest.setGemail("11@11.com");
        guest.setGname("gname");
        guestDao.saveGuest(guest);
    }

    @Test
    public void testDeleteGuest() throws Exception {
        //guestDao.deleteGuest(9);
    }

    @Test
    public void testUpdateGuest() throws Exception {

    }

    @Test
    public void testQueryGuest() throws Exception {

    }

    @Test
    public void testQueryGuestByEmail() throws Exception {

    }

    @Test
    public void testGetPageData() throws Exception {

    }

    @Test
    public void testQueryCount() throws Exception {

    }

    @Test
    public void testQueryRssGuests() throws Exception {

    }
}