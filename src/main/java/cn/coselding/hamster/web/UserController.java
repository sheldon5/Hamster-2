package cn.coselding.hamster.web;

import cn.coselding.hamster.domain.User;
import cn.coselding.hamster.dto.RegisterForm;
import cn.coselding.hamster.exception.UserExistException;
import cn.coselding.hamster.filter.LoginFilter;
import cn.coselding.hamster.service.UserService;
import cn.coselding.hamster.utils.Config;
import cn.coselding.hamster.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

/**用户登录注册Controller
 * Created by 宇强 on 2016/10/4 0004.
 */
@Controller
@RequestMapping("/user")
//user存在session中
@SessionAttributes({LoginFilter.LOGIN_TAG,UserController.UserLoginReferer})
public class UserController {

    public static final String UserLoginReferer = "UserLoginReferer";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private Config config;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String default1() {
        return login();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String default2() {
        return login();
    }

    //登陆界面
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "manage/login";
    }

    //登录操作
    @RequestMapping(value = "/login-go", method = RequestMethod.POST)
    public String loginGo(@RequestParam(value = "uname", required = false) String uname,
                          @RequestParam(value = "password", required = false) String password,
                          HttpSession session,
                          Model model) {
        User user = userService.login(uname, password);
        if (user != null) {
            model.addAttribute(LoginFilter.LOGIN_TAG, user);
            logger.info("用户登录成功...：uname="+uname);
            //跳转到登录前的请求页面，如果没有就跳转到主界面
            String referer = (String) session.getAttribute(UserLoginReferer);
            logger.info("loginGo-referer:"+referer);
            session.removeAttribute(UserLoginReferer);
            if(referer==null||referer.length()<=0){
                return "redirect:/manage/";
            }else {
                return "redirect:"+referer;
            }
        } else {
            user = new User();
            user.setUname(uname);
            model.addAttribute("loginForm", user);
            model.addAttribute("message", "用户名或密码不正确！");
            logger.warn("用户登录账号密码错误...uname="+uname);
            return "manage/login";
        }
    }

    //注册界面
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        //配置关闭注册
        if (!config.isUserRegister()) {
            return "redirect:/user/login";
        }
        return "manage/register";
    }

    //注册操作
    @RequestMapping(value = "/register-go", method = RequestMethod.POST)
    public String registerGo(@Valid RegisterForm registerForm,
                             BindingResult result,
                             Model model) {
        //配置关闭注册
        if(!config.isUserRegister()){
            return "redirect:/user/login";
        }
        //数据回显
        model.addAttribute("registerForm", registerForm);
        //表单校验
        Map<String, Object> errors = null;
        if (result.hasErrors()) {
            errors = WebUtils.validateForm(result, model);
            return "manage/register";
        }
        if (WebUtils.validatePassword2(registerForm, errors, model)) {
            return "manage/register";
        }
        //注册
        try {
            userService.register(registerForm.getUname(), registerForm.getPassword());
            logger.info("用户注册成功...：uname="+registerForm.getUname());
            return "redirect:/user/login";
        } catch (UserExistException e) {
            model.addAttribute("message", "用户名已存在");
            logger.info("用户注册失败，用户名已存在...：uname="+registerForm.getUname());
            return "manage/register";
        }
    }
}
