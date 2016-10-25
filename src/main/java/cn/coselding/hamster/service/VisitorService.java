package cn.coselding.hamster.service;

import cn.coselding.hamster.domain.Article;
import cn.coselding.hamster.domain.Comment;
import cn.coselding.hamster.domain.Guest;
import cn.coselding.hamster.dto.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**访客：访客相关的业务逻辑
 * Created by 宇强 on 2016/10/4 0004.
 */
public interface VisitorService {
    //添加客户，留言的时候自动记录客户
    @Transactional
    Guest addGuest(Guest guest);

    //添加留言
    void addComment(Guest guest, Comment comment, String contextPath);

    //分页查询留言
    @Transactional
    Page<Comment> findComments(int pagenum, String url);

    //RSS博客订阅
    @Transactional
    void rss(String email, int rss);

    //喜爱文章
    @Transactional
    int likeArticle(int artid);

    Article queryArticleInfo(int artid);

    //文章的浏览数和喜爱数
    Article getArticleInfo(int artid);

    //获取最新的三篇文章
    List<Article> getLast3Articles();
}
