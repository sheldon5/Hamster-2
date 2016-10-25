package cn.coselding.hamster.filter;

import cn.coselding.hamster.domain.User;
import cn.coselding.hamster.service.UserService;
import cn.coselding.hamster.web.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 管理页面未登陆拦截过滤器
 * Created by 宇强 on 2016/3/12 0012.
 */
public class LoginFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String LOGIN_TAG = "user";
    private UserService userService;

    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext sc = filterConfig.getServletContext();
        WebApplicationContext wac = (WebApplicationContext) sc.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        userService = wac.getBean(UserService.class);
        logger.info("登录拦截过滤器初始化成功...");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //初始化http对象
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        //检查是否已登录
        User user = (User) session.getAttribute(LOGIN_TAG);
        if (user == null) {//未登录
            //检查cookie
            Cookie[] cookies = request.getCookies();
            String username = null;
            String password = null;
            if(cookies!=null&&cookies.length>0) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("username")) {
                        username = c.getValue();
                    } else if (c.getName().equals("password")) {
                        password = c.getValue();
                    }
                }
            }
            if(username==null||password==null){
                //获取登录前被拦截的网址，等会登录成功自动跳转过去
                String referer = request.getRequestURL().toString();
                if(referer!=null&&referer.length()>0){
                    request.getSession().setAttribute(UserController.UserLoginReferer,referer);
                }
                //未登录，跳转到登录页面
                response.sendRedirect(request.getContextPath() + "/user/login");
                logger.info("Cookie为空，跳转到登录页面");
                return;
            }
            //cookie自动登录
            user = userService.login(username, password);
            if (user == null) {//cookie中帐号密码不匹配
                //获取登录前被拦截的网址，等会登录成功自动跳转过去
                String referer = request.getRequestURL().toString();
                if(referer!=null&&referer.length()>0){
                    request.getSession().setAttribute(UserController.UserLoginReferer,referer);
                }
                //未登录，跳转到登录页面
                response.sendRedirect(request.getContextPath() + "/user/login");
                logger.warn("Cookie是伪造的");
                return;
            }else{
                session.setAttribute("user",user);
                logger.info("Cookie登录成功");
            }
        }
        filterChain.doFilter(request, response);
        //这里说明登录成功，更新cookie
        Cookie username = new Cookie("username", user.getUname());
        username.setPath("/");
        username.setMaxAge(24*60*60);
        response.addCookie(username);
        Cookie password = new Cookie("password", user.getPassword());
        password.setPath("/");
        password.setMaxAge(24*60*60);
        response.addCookie(password);
    }

    public void destroy() {
        userService=null;
    }
}
