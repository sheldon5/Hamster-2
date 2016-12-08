package cn.coselding.hamster.web.manage;

import cn.coselding.hamster.domain.Category;
import cn.coselding.hamster.dto.CategoryAddResult;
import cn.coselding.hamster.dto.CategoryForm;
import cn.coselding.hamster.dto.Page;
import cn.coselding.hamster.dto.Query;
import cn.coselding.hamster.exception.ForeignKeyException;
import cn.coselding.hamster.filter.LoginFilter;
import cn.coselding.hamster.service.ArticleService;
import cn.coselding.hamster.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**标签类别管理Controller
 * Created by 宇强 on 2016/10/4 0004.
 */
@Controller
@RequestMapping(value = "/manage/category")
//user存在session中
@SessionAttributes(LoginFilter.LOGIN_TAG)
public class CategoryManager implements ServletContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ArticleService articleService;
    private String contextPath;

    public void setServletContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
    }

    //添加类别界面
    @RequestMapping(value = "/add")
    public String add(Model model) {
        model.addAttribute("method", "add");
        model.addAttribute("pageTitle", "添加类别");
        return "manage/category";
    }

    //添加类别操作
    @RequestMapping(value = "/add-go", method = RequestMethod.POST)
    public String addGo(@Valid CategoryForm categoryForm,
                        BindingResult result,
                        Model model) {
        if (result.hasErrors()) {
            WebUtils.validateForm(result, model);
            model.addAttribute("category", categoryForm);
            model.addAttribute("cid", categoryForm.getCid());
            model.addAttribute("method", "add");
            model.addAttribute("pageTitle", "添加类别");
            return "manage/category";
        }

        Category category = new Category();
        WebUtils.copyBean(categoryForm, category);
        category.setCtime(new Date());

        boolean res = articleService.addCategory(category);
        if (res) {
            model.addAttribute("message", "类型添加成功！！！");
            logger.info("类型添加成功...：cname="+categoryForm.getCname());
        }
        else {
            model.addAttribute("message", "类型已存在！！！");
            logger.info("类型已存在...：cname="+categoryForm.getCname());
        }
        model.addAttribute("url", contextPath + "/manage/category/");
        return "message";
    }

    //添加类别操作ajax
    @RequestMapping(value = "/add-go/ajax", method = RequestMethod.POST)
    @ResponseBody
    public CategoryAddResult addGoAjax(@Valid CategoryForm categoryForm,
                        BindingResult result) {
        if (result.hasErrors()) {
            return new CategoryAddResult(0,articleService.getAllCategories());
        }

        Category category = new Category();
        WebUtils.copyBean(categoryForm, category);
        category.setCtime(new Date());

        boolean res = articleService.addCategory(category);
        if (res) {
            logger.info("类型添加成功...：cname="+categoryForm.getCname());
            return new CategoryAddResult(1,articleService.getAllCategories());
        }
        else {
            logger.info("类型已存在...：cname="+categoryForm.getCname());
            return new CategoryAddResult(0,articleService.getAllCategories());
        }
    }

    //删除类别操作
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam("cid") int cid,
                         Model model) {
        try {
            articleService.deleteCategory(cid);
            logger.info("类型删除成功...：cid="+cid);
        } catch (ForeignKeyException e) {
            model.addAttribute("message", "您要删除的类别下有文章，不能删除");
            model.addAttribute("url", contextPath + "/manage/category/");
            logger.info("类型外键依赖，删除失败...：cid="+cid);
            return "message";
        }
        return "redirect:/manage/category/";
    }

    //修改类别操作
    @RequestMapping(value = "/update-go", method = RequestMethod.POST)
    public String updateGo(@Valid CategoryForm categoryForm,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            WebUtils.validateForm(result, model);
            model.addAttribute("category", categoryForm);
            model.addAttribute("cid", categoryForm.getCid());
            model.addAttribute("method", "update");
            model.addAttribute("pageTitle", "修改类别");
            return "manage/category";
        }

        Category category = new Category();
        WebUtils.copyBean(categoryForm, category);

        articleService.updateCategory(category);
        model.addAttribute("message", "类型修改成功！！！");
        model.addAttribute("url", contextPath + "/manage/category/");
        logger.info("类型修改成功...：cname="+categoryForm.getCname());
        return "message";
    }

    //修改类别界面
    @RequestMapping(value = "/update")
    public String update(@RequestParam("cid") int cid,
                         Model model) {
        CategoryForm categoryForm = new CategoryForm();
        WebUtils.copyBean(articleService.queryCategory(cid), categoryForm);
        model.addAttribute("category", categoryForm);
        model.addAttribute("cid", cid);
        model.addAttribute("method", "update");
        model.addAttribute("pageTitle", "修改类别");
        return "manage/category";
    }

    //分页查询类别界面
    @RequestMapping(value = "/list")
    public String list(Query query,
                       Model model) {
        //分页查询所有博文
        String url = contextPath + "/manage/category/";
        Page<Category> page = articleService.queryPageCategory(query.pageNumValue(), url);
        model.addAttribute("page", page);
        return "manage/category-manage";
    }

    //默认情况下，查询列表
    @RequestMapping(value = "/")
    public String default1(Query query,Model model) {
        return list(query, model);
    }

    @RequestMapping(value = "")
    public String default2(Query query,Model model) {
        return list(query, model);
    }
}
