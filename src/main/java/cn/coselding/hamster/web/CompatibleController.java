package cn.coselding.hamster.web;

import cn.coselding.hamster.dao.TemplateHandler;
import cn.coselding.hamster.domain.Article;
import cn.coselding.hamster.service.VisitorService;
import cn.coselding.hamster.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * 向旧版本兼容Controller
 * Created by 宇强 on 2016/10/7 0007.
 */
@Controller
public class CompatibleController implements ServletContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private VisitorService visitorService;
    private String contextPath;
    @Autowired
    private Config config;

    @Override
    public void setServletContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
    }

    //之前版本的网址兼容
    @RequestMapping("/blog/{cid}/{cid}-{artid}.html")
    public String oldArticle(@PathVariable("cid") int cid,
                             @PathVariable("artid") int artid,
                             Model model) {
        Article article = visitorService.getArticleInfo(artid);

        if (article == null) {
            model.addAttribute("message", "文章不存在");
            model.addAttribute("url", contextPath + "/");
            return "message";
        }

        File html = new File(config.getStaticArticlePath() + article.getStaticURL());
        if (!html.exists()) {
            model.addAttribute("message", "文章不存在");
            model.addAttribute("url", contextPath + "/");
            return "message";
        }

        logger.warn("文章兼容网址转换成功...：title=" + article.getTitle());
        return "redirect:" + article.getStaticURL();
    }

    @RequestMapping("/article/{year}/{month}/{day}/{title}/")
    public String viewArticle(@PathVariable("year") String year,
                              @PathVariable("month") String month,
                              @PathVariable("day") String day,
                              @PathVariable("title") String title,
                              Model model) {
        System.out.println(title);
        Article article = visitorService.findArticleByTitle(title);

        if (article == null) {
            model.addAttribute("message", "文章不存在");
            model.addAttribute("url", contextPath + "/");
            return "message";
        }

        File html = new File(config.getStaticArticlePath() + article.getStaticURL());
        if (!html.exists()) {
            model.addAttribute("message", "文章不存在");
            model.addAttribute("url", contextPath + "/");
            return "message";
        }

        return "forward:" + article.getStaticURL();
    }

    @RequestMapping("/article/{year}-{month}-{day}/{title}/")
    public String viewArticle1(@PathVariable("year") String year,
                              @PathVariable("month") String month,
                              @PathVariable("day") String day,
                              @PathVariable("title") String title,
                              Model model) {
        System.out.println(title);
        Article article = visitorService.findArticleByTitle(title);

        if (article == null) {
            model.addAttribute("message", "文章不存在");
            model.addAttribute("url", contextPath + "/");
            return "message";
        }

        File html = new File(config.getStaticArticlePath() + article.getStaticURL());
        if (!html.exists()) {
            model.addAttribute("message", "文章不存在");
            model.addAttribute("url", contextPath + "/");
            return "message";
        }

        return "forward:" + article.getStaticURL();
    }

    //之前旧版主页
    @RequestMapping("/index.action")
    public String oldIndex() {
        logger.warn("主页兼容网址转换成功...");
        return "redirect:/";
    }

    //之前旧版列表
    @RequestMapping("/listArticle.action")
    public String oldList() {
        logger.warn("文章列表兼容网址转换成功...");
        return "redirect:/list";
    }
}
