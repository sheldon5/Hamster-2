package cn.coselding.hamster.filter;

import cn.coselding.hamster.domain.User;
import cn.coselding.hamster.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by 宇强 on 2016/10/6 0006.
 */
public class NoLoginFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private User user = new User();
    private Config config;

    public void destroy() {
        config = null;
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;

        //开启开发者模式
        if (config.isModeDev()) {
            user.setUname("Coselding");
            user.setPassword("123456");
            user.setUid(1);
            request.getSession().setAttribute(LoginFilter.LOGIN_TAG, user);
            logger.info("开发模式免登录成功！");
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        ServletContext sc = config.getServletContext();
        WebApplicationContext wac = (WebApplicationContext) sc.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        this.config = wac.getBean(Config.class);
        logger.info("免登录过滤器初始化成功...");
    }

}
