package cn.coselding.hamster.dao;

import cn.coselding.hamster.domain.Category;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 宇强 on 2016/3/12 0012.
 */
public interface CategoryDao {
    //增
    void saveCategory(Category category);

    //删
    int deleteCategory(int cid);

    //改
    int updateCategory(Category category);

    //查单个
    Category queryCategory(int cid);

    //查单个
    Category queryCategoryByName(String cname);

    //查询所有
    List<Category> queryAll();

    //查询总数
    int getCount();

    //查询分页
    List<Category> getPageData(@Param("startindex")int startindex,@Param("pagesize")int pagesize);
}
