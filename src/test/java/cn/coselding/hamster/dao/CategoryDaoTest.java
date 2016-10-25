package cn.coselding.hamster.dao;

import cn.coselding.hamster.domain.Category;
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
public class CategoryDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void testSaveCategory() throws Exception {
        Category category = new Category();
        category.setCid(1);
        category.setCname("testC");
        category.setCtime(new Date());
        //categoryDao.saveCategory(category);
    }

    @Test
    public void testDeleteCategory() throws Exception {
        //categoryDao.deleteCategory(12);
    }

    @Test
    public void testUpdateCategory() throws Exception {

    }

    @Test
    public void testQueryCategory() throws Exception {

    }

    @Test
    public void testQueryCategoryByName() throws Exception {

    }

    @Test
    public void testQueryAll() throws Exception {

    }

    @Test
    public void testGetCount() throws Exception {

    }

    @Test
    public void testGetPageData() throws Exception {

    }
}