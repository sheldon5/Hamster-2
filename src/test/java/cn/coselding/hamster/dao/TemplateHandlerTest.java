package cn.coselding.hamster.dao;

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
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class TemplateHandlerTest {

    @Autowired
    private TemplateHandler templateHandler;

    @Test
    public void testGetConfiguration() throws Exception {
        System.out.println(templateHandler.getConfiguration());
    }

    @Test
    public void testParserTemplate() throws Exception {

    }

    @Test
    public void testStaticPage() throws Exception {

    }

    @Test
    public void testStaticAllPage() throws Exception {

    }

    @Test
    public void testFormatDate() throws Exception {
        System.out.println(templateHandler.formatDate(new Date()));
    }
}