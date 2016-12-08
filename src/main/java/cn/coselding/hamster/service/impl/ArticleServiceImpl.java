package cn.coselding.hamster.service.impl;

import cn.coselding.hamster.dao.ArticleDao;
import cn.coselding.hamster.dao.CategoryDao;
import cn.coselding.hamster.dao.GuestDao;
import cn.coselding.hamster.dao.TemplateHandler;
import cn.coselding.hamster.domain.Article;
import cn.coselding.hamster.domain.Category;
import cn.coselding.hamster.domain.Guest;
import cn.coselding.hamster.dto.Page;
import cn.coselding.hamster.email.JavaMailWithAttachment;
import cn.coselding.hamster.exception.ForeignKeyException;
import cn.coselding.hamster.utils.*;
import cn.coselding.hamster.web.manage.ArticleManager;
import org.markdown4j.Markdown4jProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员：处理文章和类别业务逻辑
 * Created by 宇强 on 2016/3/12 0012.
 */
@Service
public class ArticleServiceImpl implements cn.coselding.hamster.service.ArticleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private GuestDao guestDao;
    @Autowired
    private GlobalCache globalCache;
    @Autowired
    private Config config;
    @Autowired
    private TemplateHandler templateHandler;
    @Autowired
    private JavaMailWithAttachment javaMailWithAttachment;
    @Autowired
    private Markdown4jProcessor markdown4jProcessor;

    //得到所有类别
    public List<Category> getAllCategories() {
        //缓存中有，直接查询缓存
        if (globalCache.containsCategories())
            return globalCache.getCategories();
        //还没缓存  查询数据库

        List<Category> list = categoryDao.queryAll();
        globalCache.setCategories(list);
        return list;
    }

    //添加类别
    @Transactional
    public boolean addCategory(Category category) {
        boolean result = false;
        //如果已经有这个类别的，报错
        Category temp = categoryDao.queryCategoryByName(category.getCname());
        if (temp == null) {
            categoryDao.saveCategory(category);
            List<Category> list = categoryDao.queryAll();
            globalCache.setCategories(list);
            result = true;
        }
        return result;
    }

    //删除类别
    @Transactional
    public boolean deleteCategory(int cid) throws ForeignKeyException {
        int res = categoryDao.deleteCategory(cid);
        if (res == 0)
            throw new ForeignKeyException("类别删除失败");
        globalCache.setCategories(categoryDao.queryAll());
        return true;
    }

    //查询指定类别
    public Category queryCategory(int cid) {
        return categoryDao.queryCategory(cid);
    }

    //更新类别
    @Transactional
    public void updateCategory(Category category) {
        Category temp = categoryDao.queryCategory(category.getCid());
        temp.setCname(category.getCname());
        categoryDao.updateCategory(temp);
        globalCache.setCategories(categoryDao.queryAll());
    }

    //分页查询类别
    @Transactional
    public Page<Category> queryPageCategory(int pagenum, String url) {
        // 总记录数
        int totalrecord = (int) categoryDao.getCount();
        // 根据传递的页号查找所需显示数据
        Page<Category> page = new Page<Category>(totalrecord, pagenum);
        List<Category> list = categoryDao.getPageData(page.getStartindex(),
                page.getPagesize());
        page.setList(list);
        page.setUrl(url);
        return page;
    }

    //更新文章信息，喜爱，访问量
    public void updateArticleInfo(Article article) {
        articleDao.updateArticleInfo(article);
    }

    //得到某个类别的文章，分页
    @Transactional
    public Page<Article> getCategoryPageArticles(int cid, int pagenum, String url) {
        // 总记录数
        int totalrecord = articleDao.queryCountByCid(cid);
        // 根据传递的页号查找所需显示数据
        Page<Article> page = new Page<Article>(totalrecord, pagenum);
        List<Article> list = articleDao.getPageDataByCid(cid, page.getStartindex(), page.getPagesize());
        page.setList(list);
        page.setUrl(url);
        return page;
    }

    //分页查询文章
    @Transactional
    public Page<Article> getPageArticles(int pagenum, String url) {
        // 总记录数
        int totalrecord = (int) articleDao.queryCount();
        // 根据传递的页号查找所需显示数据
        Page<Article> page = new Page<Article>(totalrecord, pagenum);
        List<Article> list = articleDao.getPageDataInfo(page.getStartindex(), page.getPagesize());
        page.setList(list);
        page.setUrl(url);
        return page;
    }

    //搜索文章，分页
    @Transactional
    public Page<Article> searchArticle(String key, int pagenum, String url) {
        // 总记录数
        int totalrecord = articleDao.searchCount(key);
        // 根据传递的页号查找所需显示数据
        Page<Article> page = new Page<Article>(totalrecord, pagenum);
        List<Article> list = articleDao.searchPageData(key, page.getStartindex(), page.getPagesize());
        page.setList(list);
        page.setUrl(url);
        return page;
    }

    //删除文章
    @Transactional
    public void deleteArticle(int artid, String saveRootPath) {
        Article article = articleDao.queryArticleInfo(artid);
        //删除数据库记录
        articleDao.deleteArticle(artid);
        //删除静态化文件
        String path = saveRootPath + article.getStaticURL();
        //System.out.println("path -->> "+path);
        File file = new File(path);
        if (file.exists()) {
            file.delete();
            //System.out.println("File delete....");
        }
    }

    //查询指定文章
    public Article queryArticle(int artid, boolean full) {
        if (full) {
            return articleDao.queryArticle(artid);
        } else return articleDao.queryArticleInfo(artid);
    }

    private void staticArticlePage(Article article, String contextPath, String saveRootPath) {
        //静态化页面
        Map<String, Object> params = getTemplateParams(article.getArtid(), contextPath);
        templateHandler.staticAllPage(saveRootPath, params);

        //重新静态化上一篇文章，因为他的页面的下一篇超链接需要更新
        Article last = (Article) params.get("lastArticle");
        params = getTemplateParams(last.getArtid(), contextPath);
        if (params != null) {
            templateHandler.staticAllPage(saveRootPath, params);
        }

        //重新静态化下一篇文章，因为他的页面的上一篇超链接需要更新
        Article next = (Article) params.get("nextArticle");
        params = getTemplateParams(next.getArtid(), contextPath);
        if (params != null) {
            templateHandler.staticAllPage(saveRootPath, params);
        }

        //重新静态化主页
        staticIndex(contextPath, saveRootPath);
    }

    @Transactional
    public void staticIndex(String contextPath, String saveRootPath) {
        //查询首页所需动态信息
        Map<String, Object> params = getIndexParams(contextPath);
        params.put("contextPath", contextPath);
        //静态化到html文件中
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(saveRootPath + "/index.html");
            templateHandler.parserTemplate("/index.ftl", params, fos);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage() + " 主页模版文件不存在...", e);
            throw new RuntimeException(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error(e.getMessage() + "文件输出流关闭失败", e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //添加文章，静态化，邮件通知订阅用户
    @Transactional
    public Article addArticle(Article article, String contextPath, String saveRootPath) {
        //保存数据库
        articleDao.saveArticle(article);

        //建立solr索引
        // TODO 先注释
        //SolrjUtils.createIndex(article);

        //先删除原来的静态化文件
        TemplateHandler.ArticlePath staticURL = templateHandler.getArticlePath(article);
        File html = new File(saveRootPath + staticURL.getFilePath() + ".html");
        if (html.exists()) {
            html.delete();
        }

        //标记为发布，静态化文件
        if (article.getDeploy() == 0) {
            //静态化页面,更新类别列表
            List<Category> list = categoryDao.queryAll();
            globalCache.setCategories(list);

            //静态化路径
            article.setStaticURL(staticURL.getUrlPath());
            //储存静态化页面路径
            articleDao.updateArticleInfo(article);

            //静态化操作
            staticArticlePage(article, contextPath, saveRootPath);

            //查询已订阅的用户
            List<Guest> guests = guestDao.queryRssGuests();
            //发送邮件
            javaMailWithAttachment.sendRSS(article, guests, contextPath, true);
        } else {
            //不发布，静态化路径为空
            article.setStaticURL("");
            //储存静态化页面路径
            articleDao.updateArticleInfo(article);
        }
        return article;
    }

    //修改文章内容，静态化，通知订阅用户
    @Transactional
    public Article updateArticle(Article temp, String contextPath, String saveRootPath) {

        Article article = articleDao.queryArticle(temp.getArtid());
        article.setType(temp.getType());
        article.setCid(temp.getCid());
        article.setTitle(temp.getTitle());
        article.setTime(temp.getTime());
        article.setMeta(temp.getMeta());
        article.setTop(temp.getTop());
        article.setEditor(temp.getEditor());
        article.setMd(temp.getMd());
        article.setDeploy(temp.getDeploy());
        article.setContent(temp.getContent());
        //保存数据库
        articleDao.updateArticle(article);

        //建立solr索引
        // TODO 先注释
        //SolrjUtils.createIndex(article);

        //先删除原来的静态化文件
        TemplateHandler.ArticlePath staticURL = templateHandler.getArticlePath(article);
        File html = new File(saveRootPath + staticURL.getFilePath() + ".html");
        if (html.exists()) {
            html.delete();
        }

        //标记为发布，静态化文件
        if (article.getDeploy() == 0) {
            List<Category> list = categoryDao.queryAll();
            globalCache.setCategories(list);

            //静态化路径
            article.setStaticURL(staticURL.getUrlPath());
            //储存静态化页面路径
            articleDao.updateArticleInfo(article);

            //静态化操作
            staticArticlePage(article, contextPath, saveRootPath);

            //查询已订阅的用户
            List<Guest> guests = guestDao.queryRssGuests();
            //发送邮件
            javaMailWithAttachment.sendRSS(article, guests, contextPath, false);
        } else {
            //不发布，静态化路径为空
            article.setStaticURL("");
            //储存静态化页面路径
            articleDao.updateArticleInfo(article);
        }
        return article;
    }

    //得到freemarker模版文件所需参数
    //@Transactional
    private Map<String, Object> getTemplateParams(int artid, String contextPath) {
        Article article = articleDao.queryArticle(artid);
        if (article == null)
            return null;
        //如果是markdown编辑器，先解析md为html代码
        if (article.getEditor() == Article.EDITOR_MD) {
            try {
                String html = markdown4jProcessor.process(article.getMd());
                article.setContent(ServiceUtils.removeHtml(html));
                logger.info("markdown转换html成功：\n" + article.getContent());
            } catch (IOException e) {
                logger.error(e.getMessage() + "Markdown解析错误", e);
                throw new RuntimeException(e);
            }
        }
        //要静态化的文章内容现在在content中，在此设置img的宽高
        article.setContent(ServiceUtils.staticImageSize(article.getContent()));

        //本文章的类别
        Category category = categoryDao.queryCategory(article.getCid());

        //最新3篇的文章
        List<Article> lastArticlesList = articleDao.queryLast3Articles();

        //所有类别
        List<Category> categories = null;
        //先查缓存
        if (globalCache.containsCategories())
            categories = globalCache.getCategories();
        else {
            categories = categoryDao.queryAll();
            globalCache.setCategories(categories);
        }

        //下一篇
        Article next = articleDao.queryNextArticleInfo(article.getTime());
        if (next == null) {
            next = new Article();
            next.setStaticURL("#");
            next.setTitle("这是最后一篇了哦！");
        }

        //上一篇文章
        Article last = articleDao.queryLastArticleInfo(article.getTime());
        if (last == null) {
            last = new Article();
            last.setStaticURL("#");
            last.setTitle("这是第一篇哦！");
        }

        String typeString = "";
        if (article.getType().equals("原创")) {
            String url = config.getServerHost() + contextPath + article.getStaticURL() + "/";
            typeString = "<p>本文为博主原创，允许转载，但请声明原文地址：<a href=\"" + url + "\">" + url + "</a></p>";
        }

        //封装模版所需参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("article", article);
        params.put("categoryList", categories);
        params.put("nextArticle", next);
        params.put("lastArticle", last);
        params.put("lastArticlesList", lastArticlesList);
        params.put("category", category);
        params.put("time", WebUtils.toDateTimeString(article.getTime()));
        params.put("typeString", typeString);
        params.put("contextPath", contextPath);
        params.put("staticURL", article.staticPath());
        return params;
    }

    //得到主页模板引擎参数
    @Transactional
    public Map<String, Object> getIndexParams(String contextPath) {
        //最新三篇文章
        List<Article> lastArticles = articleDao.queryLast3Articles();

        //列表顶置四篇文章
        List<Article> topArticles = articleDao.queryTop4Articles();
        System.out.println(topArticles);

        //所有类别
        List<Category> categories = null;
        //先查缓存
        if (globalCache.containsCategories())
            categories = globalCache.getCategories();
        else {
            categories = categoryDao.queryAll();
            globalCache.setCategories(categories);
        }

        //封装模版所需参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categories", categories);
        params.put("topArticles", topArticles);
        params.put("lastArticlesList", lastArticles);

        return params;
    }

    @Transactional
    public boolean reloadAllArticles(String contextPath, String saveRootPath) {
        // 总记录数
        long count = articleDao.queryCount();
        Page<Article> page = new Page<Article>((int) count, 1);
        //总页数
        int pageCount = page.getTotalpage();
        //遍历所有页
        for (int i = 1; i <= pageCount; i++) {
            //查询页
            page = new Page<Article>((int) count, i);
            List<Article> arts = articleDao.getPageDataInfo(page.getStartindex(), page.getPagesize());
            //遍历页中的所有文章
            for (Article a : arts) {
                a.setStaticURL(templateHandler.getArticlePath(a).getUrlPath());
                articleDao.updateArticleInfo(a);
                Map<String, Object> params = getTemplateParams(a.getArtid(), contextPath);
                //静态化页面
                templateHandler.staticAllPage(saveRootPath, params);
            }
        }
        return true;
    }

    @Transactional
    public boolean reloadArticle(int artid, String contextPath, String saveRootPath) {
        Article article = articleDao.queryArticleInfo(artid);
        article.setStaticURL(templateHandler.getArticlePath(article).getUrlPath());
        articleDao.updateArticleInfo(article);
        //静态化页面
        Map<String, Object> params = getTemplateParams(artid, contextPath);
        templateHandler.staticAllPage(saveRootPath, params);
        return true;
    }

    @Transactional
    public void lookArticle(int artid) {
        Article article = articleDao.queryArticleInfo(artid);
        if (article != null) {
            article.setLooked(article.getLooked() + 1);
            articleDao.updateArticleInfo(article);
        }
    }

    @Transactional
    public void formatFromDB() {
        // 总记录数
        long count = articleDao.queryCount();
        Page<Article> page = new Page<Article>((int) count, 1);
        //总页数
        int pageCount = page.getTotalpage();
        //遍历所有页
        for (int i = 1; i <= pageCount; i++) {
            //查询页
            page = new Page<Article>((int) count, i);
            List<Article> arts = articleDao.getPageData(page.getStartindex(), page.getPagesize());
            //遍历页中的所有文章
            for (Article article : arts) {
                //更新格式化文章内容
                if (article.getContent() != null && article.getContent().length() > 0) {
                    article.setContent(ServiceUtils.removeHtml(article.getContent()));
                }
                //格式化meta
                String meta = WebUtils.getArticleMeta(article, markdown4jProcessor);
                article.setMeta(ServiceUtils.staticImageSize(meta));
                //历史原因，urlTitle为空的用hashcode取代
                if (article.getUrlTitle() == null || article.getUrlTitle().length() < 1) {
                    article.setUrlTitle(article.getTitle().hashCode() + "");
                }
                articleDao.updateArticle(article);
            }
        }
    }
}
