package cn.coselding.hamster.web.manage;

import cn.coselding.hamster.domain.User;
import cn.coselding.hamster.dto.Page;
import cn.coselding.hamster.dto.Query;
import cn.coselding.hamster.dto.RegisterForm;
import cn.coselding.hamster.exception.ForeignKeyException;
import cn.coselding.hamster.exception.UserExistException;
import cn.coselding.hamster.filter.LoginFilter;
import cn.coselding.hamster.service.UserService;
import cn.coselding.hamster.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.util.Map;

/**管理员用户管理Controller
 * Created by 宇强 on 2016/10/4 0004.
 */
@Controller
@RequestMapping(value = "/manage/user")
//user存在session中
@SessionAttributes(LoginFilter.LOGIN_TAG)
public class UserManager implements ServletContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    private String contextPath;
    private String realRootPath;

    public void setServletContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
        realRootPath = servletContext.getRealPath("");
    }

    //添加用户界面
    @RequestMapping(value = "/add")
    public String add(Model mode) {
        mode.addAttribute("method", "add");
        mode.addAttribute("pageTitle", "添加用户");
        return "manage/user";
    }

    //添加用户操作
    @RequestMapping(value = "/add-go", method = RequestMethod.POST)
    public String addGo(@Valid RegisterForm registerForm,
                        BindingResult result,
                        Model model) {
        model.addAttribute("registerForm", registerForm);
        model.addAttribute("method", "add");
        model.addAttribute("pageTitle", "添加用户");

        Map<String,Object> errors = null;
        if (result.hasErrors()) {
            errors = WebUtils.validateForm(result, model);
            return "manage/user";
        }
        if(WebUtils.validatePassword2(registerForm,errors,model)){
            return "manage/user";
        }

        try {
            userService.register(registerForm.getUname(), registerForm.getPassword());
            model.addAttribute("message", "用户添加成功！！！");
            logger.info("用户添加成功...：uname="+registerForm.getUname());
        } catch (UserExistException e) {
            model.addAttribute("message", "用户已存在...：uname="+registerForm.getUname());
            logger.info("用户已存在！！！");
        }
        model.addAttribute("url", contextPath + "/manage/user/");
        return "message";
    }

    //删除用户操作
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam("uid") int uid,
                         Model model) {
        try {
            userService.deleteUser(uid);
            logger.info("用户删除成功...：uid="+uid);
        } catch (ForeignKeyException e) {
            model.addAttribute("message", "您要删除的用户下有文章，不能删除");
            model.addAttribute("url", contextPath + "/manage/user/");
            logger.info("用户外键依赖，删除失败...：uid="+uid);
            return "message";
        }
        return "redirect:/manage/user/";
    }

    //修改用户操作
    @RequestMapping(value = "/update-go", method = RequestMethod.POST)
    public String updateGo(@Valid RegisterForm registerForm,
                           BindingResult result,
                           Model model) {
        model.addAttribute("registerForm", registerForm);
        model.addAttribute("uid", registerForm.getUid());
        model.addAttribute("method", "update");
        model.addAttribute("pageTitle", "修改用户");

        Map<String, Object> errors = null;
        if (result.hasErrors()) {
            errors = WebUtils.validateForm(result, model);
            return "manage/user";
        }
        if(WebUtils.validatePassword2(registerForm,errors,model)){
            return "manage/user";
        }

        User addUser = new User();
        WebUtils.copyBean(registerForm, addUser);

        userService.updateUser(addUser);
        model.addAttribute("message", "用户信息修改成功...：uname="+registerForm.getUname());
        model.addAttribute("url", contextPath + "/manage/user/");
        logger.info("用户修改成功");
        //跳转回之前的页面
        return "message";
    }

    //修改用户界面
    @RequestMapping(value = "/update")
    public String update(@RequestParam("uid") int uid,
                         Model model) {
        RegisterForm registerForm = new RegisterForm();
        WebUtils.copyBean(userService.queryUser(uid), registerForm);
        model.addAttribute("registerForm", registerForm);
        model.addAttribute("uid", uid);
        model.addAttribute("method", "update");
        model.addAttribute("pageTitle", "修改用户");
        return "manage/user";
    }

    //分页查询用户列表
    @RequestMapping(value = "/list")
    public String list(Query query,
                       Model model) {
        //分页查询所有用户
        String url = contextPath + "/manage/user/";
        Page<User> page = userService.queryPageUsers(query.pageNumValue(), url);
        model.addAttribute("page", page);
        return "manage/user-manage";
    }

    //默认情况下，查询用户列表
    @RequestMapping(value = "/")
    public String default1(Query query,Model model) {
        return list(query, model);
    }

    @RequestMapping(value = "")
    public String default2(Query query,Model model) {
        return list(query, model);
    }
}
