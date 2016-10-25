package cn.coselding.hamster.dao;

import cn.coselding.hamster.domain.Article;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

/**
 * Created by 宇强 on 2016/3/12 0012.
 */
public interface ArticleDao {
    //增
    int saveArticle(Article article);

    //删
    void deleteArticle(int artid);

    //改
    int updateArticle(Article article);

    int updateArticleInfo(Article article);

    //查单个
    Article queryArticle(int artid);
    //下一篇
    Article queryNextArticleInfo(Date time);
    //上一篇
    Article queryLastArticleInfo(Date time);
    //查单个
    Article queryArticleInfo(int artid);

    //查总数
    int queryCount();
    //查分页
    List<Article> getPageDataInfo(@Param("startindex")int startindex,@Param("pagesize")int pagesize);
    List<Article> getPageData(@Param("startindex")int startindex,@Param("pagesize")int pagesize);

    //通过cid查总数
    int queryCountByCid(int cid);
    //通过cid查分页
    List<Article> getPageDataByCid(@Param("cid")int cid,@Param("startindex")int startindex,@Param("pagesize")int pagesize);

    //通过搜索查总数
    int searchCount(@Param("key")String key);
    //通过搜索查分页
    List<Article> searchPageData(@Param("key")String key, @Param("startindex")int startindex,@Param("pagesize")int pagesize);

    List<Article> queryTop4Articles();
    List<Article> queryLast3Articles();
}
