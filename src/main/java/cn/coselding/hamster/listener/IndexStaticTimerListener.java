package cn.coselding.hamster.listener; /**
 * Created by 宇强 on 2016/6/19 0019.
 */

import cn.coselding.hamster.service.ArticleService;
import cn.coselding.hamster.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.*;
import java.util.Timer;
import java.util.TimerTask;

public class IndexStaticTimerListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    // Public constructor is required by servlet spec
    public IndexStaticTimerListener() {

    }

    private String contextPath;
    private Config config;
    private ArticleService articleService;
    private Timer timer;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        logger.info("TimerListener...");
        ServletContext sc = sce.getServletContext();
        WebApplicationContext wac = (WebApplicationContext) sc.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        articleService = wac.getBean(ArticleService.class);
        contextPath = sc.getContextPath();
        config = wac.getBean(Config.class);

        //每隔半小时更新一次主页
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                articleService.staticIndex(contextPath, config.getStaticIndexPath());
                logger.info("主页定时静态化成功...");
            }
        }, 60 * 1000, 30 * 60 * 1000);
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        timer.cancel();
        timer = null;
        articleService = null;
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
      /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
      /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }
}
