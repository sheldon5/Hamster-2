package cn.coselding.hamster.web;

import cn.coselding.hamster.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

/**
 * 文章页面访问Controller
 * Created by 宇强 on 2016/10/6 0006.
 */
@Controller
@RequestMapping("/article")
public class ArticleController implements ServletContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    private String realRootPath;
    @Autowired
    private Config config;
    private String contextPath;

    @Override
    public void setServletContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
//        realRootPath = servletContext.getRealPath("/");
    }

    @RequestMapping("/{year}-{month}-{day}/{title}/")
    public String viewArticle(@PathVariable("year") String year,
                              @PathVariable("month") String month,
                              @PathVariable("day") String day,
                              @PathVariable("title") String title,
                              Model model) {
        StringBuilder builder = new StringBuilder();
        builder.append("/article/")
                .append(year)
                .append("-")
                .append(month)
                .append("-")
                .append(day)
                .append("/")
                .append(title)
                .append(".html");

        File html = new File(config.getStaticArticlePath() + builder.toString());
        if (!html.exists()) {
            model.addAttribute("message", "文章不存在");
            model.addAttribute("url", contextPath + "/");
            return "message";
        }

        return "forward:" + builder.toString();
    }
}
