package cn.coselding.hamster.service;

import cn.coselding.hamster.domain.Article;
import cn.coselding.hamster.domain.Category;
import cn.coselding.hamster.dto.Page;
import cn.coselding.hamster.exception.ForeignKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**管理员：处理文章和类别业务逻辑
 * Created by 宇强 on 2016/10/4 0004.
 */
public interface ArticleService {
    //得到所有类别
    List<Category> getAllCategories();

    //添加类别
    @Transactional
    boolean addCategory(Category category);

    //删除类别
    @Transactional
    boolean deleteCategory(int cid) throws ForeignKeyException;

    //查询指定类别
    Category queryCategory(int cid);

    //更新类别
    @Transactional
    void updateCategory(Category category);

    //分页查询类别
    @Transactional
    Page<Category> queryPageCategory(int pagenum, String url);

    //更新文章信息，喜爱，访问量
    void updateArticleInfo(Article article);

    //得到某个类别的文章，分页
    @Transactional
    Page<Article> getCategoryPageArticles(int cid, int pagenum, String url);

    //分页查询文章
    @Transactional
    Page<Article> getPageArticles(int pagenum, String url);

    //搜索文章，分页
    @Transactional
    Page<Article> searchArticle(String key, int pagenum, String url);

    //删除文章
    @Transactional
    void deleteArticle(int artid, String realPath);

    //查询指定文章
    Article queryArticle(int artid,boolean full);

    //TODO
    //添加文章，静态化，邮件通知订阅用户
    @Transactional
    Article addArticle(Article article, String contextPath, String realRootPath);

    //TODO
    //修改文章内容，静态化，通知订阅用户
    @Transactional
    Article updateArticle(Article temp, String contextPath, String realRootPath);

    //Map<String, Object> getTemplateParams(int artid, String contextPath);
    //TODO
    //得到主页模板引擎参数
    @Transactional
    Map<String, Object> getIndexParams(String contextPath);
    void staticIndex(String contextPath,String realRootPath);

    //TODO
    @Transactional
    boolean reloadAllArticles(String contextPath, String realRootPath);

    //TODO
    @Transactional
    boolean reloadArticle(int artid, String contextPath, String realRootPath);

    @Transactional
    void lookArticle(int artid);

    @Transactional
    void formatFromDB();
}
