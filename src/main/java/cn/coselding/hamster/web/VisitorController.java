package cn.coselding.hamster.web;

import cn.coselding.hamster.domain.Article;
import cn.coselding.hamster.domain.Category;
import cn.coselding.hamster.dto.Page;
import cn.coselding.hamster.dto.Query;
import cn.coselding.hamster.service.ArticleService;
import cn.coselding.hamster.service.VisitorService;
import cn.coselding.hamster.utils.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**访客操作Controller
 * Created by 宇强 on 2016/10/4 0004.
 */
@Controller
public class VisitorController implements ServletContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String ARTICLE_VIEW_TOKEN = "view";
    public static final String LIKE_TOKEN = "like";

    @Autowired
    private ArticleService articleService;
    @Autowired
    private VisitorService visitorService;
    private String contextPath;

    @Override
    public void setServletContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
    }

    @RequestMapping(value = "/like")
    @ResponseBody
    public Article like(@RequestParam("artid") int artid,
                        HttpSession session) {
        //还没like过就能like
        if (session.getAttribute(LIKE_TOKEN + artid) == null) {
            //刷新数据库
            int likes = visitorService.likeArticle(artid);
            //设置当前的用户session已经like过了
            session.setAttribute(LIKE_TOKEN + artid, "true");
            logger.info("用户喜爱文章成功...：artid="+artid);
        }

        Article article = visitorService.queryArticleInfo(artid);
        //获取请求前的页面
        return article;
    }

    @RequestMapping(value = "/like/")
    @ResponseBody
    public Article like2(@RequestParam("artid") int artid,
                        HttpSession session) {
        return like(artid, session);
    }

    /**
     * 刷新文章的喜爱数和浏览数
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/refresh")
    @ResponseBody
    public Article refresh(@RequestParam("artid") int artid,
                           HttpSession session) {
        //防止同一用户session添加多次访问量
        boolean isNew = false;
        //还没看过就能添加访问量
        if (session.getAttribute(ARTICLE_VIEW_TOKEN + artid) == null) {
            isNew = true;
            //设置当前的用户session已经看过文章了
            session.setAttribute(ARTICLE_VIEW_TOKEN + artid, "true");
        }
        //第一次访问，增加访问树
        if (isNew) {
            articleService.lookArticle(artid);
            logger.info("用户访问文章成功...：artid="+artid);
        }
        Article article = visitorService.getArticleInfo(artid);
        //回写响应数据
        return article;
    }

    @RequestMapping(value = "/refresh/")
    @ResponseBody
    public Article refresh2(@RequestParam("artid") int artid,
                            HttpSession session) {
        return refresh(artid,session);
    }

    //刷新标签列表
    @RequestMapping(value = "/categories")
    @ResponseBody
    public List<Category> refreshCategories() {
        List<Category> categories = articleService.getAllCategories();
        System.out.println("更新主页类别");
        //回写响应数据
        return categories;
    }

    //刷新最新文章列表
    @RequestMapping(value = "/last3")
    @ResponseBody
    public List<Article> refreshLast3() {
        System.out.println("更新主页最新文章");
        return visitorService.getLast3Articles();
    }

    //文章搜索
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(Query query,
                         Model model) {
        //返回地址，分页查询使用
        String url = contextPath + "/search";
        Page<Article> page = articleService.searchArticle(query.keyValue(), query.pageNumValue(), url);
        model.addAttribute("page", page);
        //js将所有连接重置的参数
        model.addAttribute("key", query.keyValue());

        //网页中的其他信息
        Map<String, Object> params = articleService.getIndexParams(contextPath);
        model.addAttribute("params", params);
        return "list";
    }

    @RequestMapping(value = "/search/", method = RequestMethod.POST)
    public String search2(Query query,
                          Model model) {
        return search(query, model);
    }

    @RequestMapping(value = "/list")
    public String list(Query query,
                       Model model) {
        //返回更新后的列表地址
        String url = contextPath + "/list";
        Page<Article> page = null;

        int cid = query.cidValue();
        //获取分页
        if (cid == -1) {
            page = articleService.getPageArticles(query.pageNumValue(), url);
        } else {
            page = articleService.getCategoryPageArticles(cid, query.pageNumValue(), url);
        }
        model.addAttribute("page", page);
        //js将所有连接重置的参数
        model.addAttribute("cid", cid);

        //网页中的其他信息
        Map<String, Object> params = articleService.getIndexParams(contextPath);
        model.addAttribute("params", params);
        model.addAttribute("categories", params.get("categories"));

        return "list";
    }

    @RequestMapping(value = "/list/")
    public String list2(Query query,
                        Model model) {
        return list(query, model);
    }
}
