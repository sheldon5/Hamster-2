package cn.coselding.hamster.web;

import cn.coselding.hamster.dto.RssEmailForm;
import cn.coselding.hamster.service.ArticleService;
import cn.coselding.hamster.service.VisitorService;
import cn.coselding.hamster.utils.GlobalCache;
import cn.coselding.hamster.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**博客订阅Controller
 * Created by 宇强 on 2016/10/4 0004.
 */
@Controller
@RequestMapping("/rss")
public class RssController implements ServletContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArticleService articleService;
    @Autowired
    private VisitorService visitorService;
    private String contextPath;

    public void setServletContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String rssBlogUI1(Model model) {
        model.addAttribute("categories", articleService.getAllCategories());
        return "rss";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String rssBlogUI2(Model model) {
        return rssBlogUI1(model);
    }

    //订阅
    @RequestMapping(value = "/yes", method = RequestMethod.POST)
    public String rss(@Valid RssEmailForm rssEmailForm,
                      BindingResult result,
                      Model model) {
        if (result.hasErrors()) {
            WebUtils.validateForm(result, model);
            model.addAttribute("gemail", rssEmailForm.getGemail());
            return "rss";
        }

        visitorService.rss(rssEmailForm.getGemail(), GlobalCache.RSS_TRUE);
        model.addAttribute("categories", articleService.getAllCategories());
        model.addAttribute("message", "订阅成功！");
        model.addAttribute("url", contextPath+"/");
        logger.info("博客订阅成功...：gemail="+rssEmailForm.getGemail());
        return "message";
    }

    //取消订阅
    @RequestMapping(value = "/no")
    public String notRss(@Valid RssEmailForm rssEmailForm,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            WebUtils.validateForm(result, model);
            model.addAttribute("gemail", rssEmailForm.getGemail());
            return "rss";
        }

        visitorService.rss(rssEmailForm.getGemail(), GlobalCache.RSS_FALSE);
        model.addAttribute("categories", articleService.getAllCategories());
        model.addAttribute("message", "您已取消对本博客的订阅！");
        model.addAttribute("url", contextPath+"/");
        logger.info("博客取消订阅成功...：gemail="+rssEmailForm.getGemail());
        return "message";
    }
}
