package cn.coselding.hamster.dao;

import cn.coselding.hamster.domain.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 宇强 on 2016/3/12 0012.
 */
public interface DraftDao {
    void setDeploy(@Param("artid")int artid,@Param("deploy")int deploy);
    //查总数
    int queryCount();
    List<Article> getPageData(@Param("startindex")int startindex, @Param("pagesize")int pagesize);
}
