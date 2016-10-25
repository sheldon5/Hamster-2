package cn.coselding.hamster.service.impl;

import cn.coselding.hamster.dao.ArticleDao;
import cn.coselding.hamster.dao.CommentDao;
import cn.coselding.hamster.dao.GuestDao;
import cn.coselding.hamster.domain.*;
import cn.coselding.hamster.dto.Page;
import cn.coselding.hamster.email.JavaMailWithAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**访客：访客相关的业务逻辑
 * Created by 宇强 on 2016/3/13 0013.
 */
@Service
public class VisitorServiceImpl implements cn.coselding.hamster.service.VisitorService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GuestDao guestDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private JavaMailWithAttachment javaMailWithAttachment;

    //添加客户，留言的时候自动记录客户
    @Transactional
    public Guest addGuest(Guest guest) {
        //邮箱已存在
        Guest g = guestDao.queryGuestByEmail(guest.getGemail());
        if (g != null) {
            g.setGname(guest.getGname());
            guestDao.updateGuest(g);
            return g;
        }
        //邮箱不存在
        guestDao.saveGuest(guest);
        return guest;
    }

    //添加留言
    public void addComment(Guest guest, Comment comment, String contextPath) {
        //通知我有人留言
        javaMailWithAttachment.sendCommentNotice(guest, comment, contextPath);
        commentDao.saveComment(comment);
    }

    //分页查询留言
    @Transactional
    public Page<Comment> findComments(int pagenum, String url) {
        // 总记录数
        int totalrecord = (int) commentDao.queryPassCount();
        // 根据传递的页号查找所需显示数据
        Page<Comment> page = new Page<Comment>(totalrecord, pagenum);
        List<Comment> list = commentDao.getPassPageData(page.getStartindex(),
                page.getPagesize());
        page.setList(list);
        page.setUrl(url);
        return page;
    }

    //RSS博客订阅
    @Transactional
    public void rss(String email, int rss) {
        Guest guest = guestDao.queryGuestByEmail(email);
        if (guest == null) {
            guest = new Guest();
            guest.setGname("匿名用户");
            guest.setGemail(email);
            guest.setRss(rss);
            guestDao.saveGuest(guest);
        } else {
            guest.setRss(rss);
            guestDao.updateGuest(guest);
        }
    }

    //查询指定文章
    public Article queryArticleInfo(int artid) {
        return articleDao.queryArticleInfo(artid);
    }

    //喜爱文章
    @Transactional
    public int likeArticle(int artid) {
        Article article = articleDao.queryArticleInfo(artid);
        article.setLikes(article.getLikes() + 1);
        articleDao.updateArticleInfo(article);
        return article.getLikes();
    }

    //文章的浏览数和喜爱数
    public Article getArticleInfo(int artid) {
        return articleDao.queryArticleInfo(artid);
    }

    //获取最新的三篇文章
    public List<Article> getLast3Articles(){
        return articleDao.queryLast3Articles();
    }
}
