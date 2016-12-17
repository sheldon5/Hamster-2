package cn.coselding.hamster.web.manage;

import cn.coselding.hamster.dto.CommonMessage;
import cn.coselding.hamster.enums.Status;
import cn.coselding.hamster.service.ThemeService;
import cn.coselding.hamster.dto.Theme;
import cn.coselding.hamster.utils.FSAuthorityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 宇强 on 2016/10/23 0023.
 */
@Controller
@RequestMapping("/manage/theme")
public class ThemeManager implements ServletContextAware{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ThemeService themeService;
    @Autowired
    private FSAuthorityUtil fsAuthorityUtil;
    private String contextPath;
    private String realRootPath;

    public void setServletContext(ServletContext servletContext) {
        contextPath=servletContext.getContextPath();
        realRootPath=servletContext.getRealPath("/");
    }

    @RequestMapping("/list")
    public String list(Model model){
        File parent = new File(realRootPath+"/WEB-INF/themes");
        List<Theme> themes = themeService.queryAllThemes(parent);
        logger.info("主题查看："+themes);
        model.addAttribute("themes",themes);
        return "manage/theme-manage";
    }

    @RequestMapping("/")
    public String default1(Model model){
        return list(model);
    }

    @RequestMapping("")
    public String default2(Model model){
        return list(model);
    }

    @RequestMapping("/change")
    @ResponseBody
    public CommonMessage change(@RequestParam("name")String name,
                         Model model) {
        Map<String,Object> res = new HashMap<String,Object>();
        File theme = new File(realRootPath+"/WEB-INF/themes/"+name);
        if(!theme.exists()){
            res.put("state",0);
            res.put("message","主题不存在！");
            logger.info("主题不存在:"+name);
            return new CommonMessage(res,Status.AUTH_ERROR);
        }
        try {
            //主题存在了,加载主题
            themeService.loadTheme(name);
            res.put("state", 1);
            res.put("message", "主题切换成功");
            logger.info("主题切换成功:"+name);

            fsAuthorityUtil.passAuthority();
            return CommonMessage.success(res);
        }catch (Exception e){
            e.printStackTrace();
            res.put("state",0);
            res.put("message","主题加载失败！");
            logger.info("主题加载失败:"+name);
            return new CommonMessage(res,Status.SERVER_ERROR);
        }
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("name")String name,
                         Model model){
        File theme = new File(realRootPath+"/WEB-INF/themes/"+name);
        if(!theme.exists()){
            model.addAttribute("message","主题不存在！");
            logger.info("主题不存在:"+name);
            return "message";
        }
        //theme is exist
        themeService.deleteTheme(name);
        model.addAttribute("message","主题删除成功！");
        model.addAttribute("url",contextPath+"/manage/theme");
        logger.info("主题删除成功:"+name);
        return "message";
    }

    @RequestMapping("/add")
    @ResponseBody
    public CommonMessage add(@RequestParam("name")String name,
                                  @RequestParam(value = "logo",required = false)MultipartFile logo){
        Map<String,Object> res = new HashMap<String,Object>();
        try {
            themeService.saveTheme(name, logo);
            res.put("state", 1);
            res.put("message", "主题保存成功！");
            logger.info("主题保存成功:"+name);
            return CommonMessage.success(res);
        }catch (Exception e){
            e.printStackTrace();
            res.put("state", 0);
            res.put("message", "主题保存失败！");
            logger.info("主题保存失败:"+name);
            return new CommonMessage(res,Status.SERVER_ERROR);
        }
    }
}
