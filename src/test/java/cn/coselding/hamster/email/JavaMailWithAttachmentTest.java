package cn.coselding.hamster.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by 宇强 on 2016/10/4 0004.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class JavaMailWithAttachmentTest {

    @Autowired
    private JavaMailWithAttachment javaMailWithAttachment;

    @Test
    public void testJavaMailWithAttachment() throws Exception {
        System.out.println(javaMailWithAttachment);
    }

    @Test
    public void testSendEmail() throws Exception {
        File affix = new File("D:\\1.txt");
        javaMailWithAttachment.doSendHtmlEmail("邮件主题", "邮件内容", "1098129797@qq.com", affix);//
    }
}