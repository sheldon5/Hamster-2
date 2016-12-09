package cn.coselding.hamster.service;

import cn.coselding.hamster.utils.Config;
import cn.coselding.hamster.utils.FSAuthorityUtil;
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
@ContextConfiguration({"classpath:spring/spring-*.xml"})
public class ArticleServiceTest {

    @Autowired
    private Config config;

    @Test
    public void testGetAllCategories() throws Exception {
        FSAuthorityUtil fsAuthorityUtil = new FSAuthorityUtil(config);
        System.out.println(config.getFsAuthCommand());
    }

    @Test
    public void testAddCategory() throws Exception {

    }

    @Test
    public void testDeleteCategory() throws Exception {

    }

    @Test
    public void testQueryCategory() throws Exception {

    }

    @Test
    public void testUpdateCategory() throws Exception {

    }

    @Test
    public void testQueryPageCategory() throws Exception {

    }

    @Test
    public void testUpdateArticleInfo() throws Exception {

    }

    @Test
    public void testGetCategoryPageArticles() throws Exception {

    }

    @Test
    public void testGetPageArticles() throws Exception {

    }

    @Test
    public void testSearchArticle() throws Exception {

    }

    @Test
    public void testDeleteArticle() throws Exception {

    }

    @Test
    public void testQueryArticle() throws Exception {

    }

    @Test
    public void testAddArticle() throws Exception {

    }

    @Test
    public void testUpdateArticle() throws Exception {

    }

    @Test
    public void testGetIndexParams() throws Exception {

    }

    @Test
    public void testReloadAllArticles() throws Exception {

    }

    @Test
    public void testReloadArticle() throws Exception {

    }

    @Test
    public void testLookArticle() throws Exception {

    }

    @Test
    public void testFormatFromDB() throws Exception {

    }
}