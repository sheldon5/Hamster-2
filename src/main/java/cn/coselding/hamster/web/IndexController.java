package cn.coselding.hamster.web;

import cn.coselding.hamster.service.ArticleService;
import cn.coselding.hamster.utils.Config;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**主页相关Controller
 * Created by 宇强 on 2016/10/6 0006.
 */
@Controller
public class IndexController implements ServletContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArticleService articleService;
    @Autowired
    private Config config;
    private String contextPath;
//    private String realRootPath;

    @Override
    public void setServletContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
//        realRootPath = servletContext.getRealPath("/");
    }

    private String getIndexPath(HttpServletResponse response,Model model) {
        if(config.isIndexDynamic()){
            Map<String,Object> params = articleService.getIndexParams(contextPath);
            model.addAttribute("params",params);
            logger.info("动态主页访问...");
            return "index";
        }else {
            File file = new File(config.getStaticIndexPath()+"/index.html");
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                IOUtils.copy(fis,response.getOutputStream());
                logger.info("静态主页访问");
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
                try {
                    response.sendError(404);
                } catch (IOException e1) {
                    throw new RuntimeException(e);
                }
            }finally {
                if(fis!=null){
                    try {
                        fis.close();
                        fis=null;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    if(response.getOutputStream()!=null){
                        response.getOutputStream().close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return "forward:/index.html";
        }
    }

//    @RequestMapping("")
//    public String default1(HttpServletResponse response,Model model) {
//        return getIndexPath(response,model);
//    }
//
//    @RequestMapping("/")
//    public String default2(HttpServletResponse response,Model model) {
//        return getIndexPath(response,model);
//    }

//    @RequestMapping("/index")
//    public String default3(HttpServletResponse response,Model model) {
//        return getIndexPath(response,model);
//    }

    @RequestMapping("/index/")
    public String default4(HttpServletResponse response,Model model) {
        return getIndexPath(response,model);
    }
}
