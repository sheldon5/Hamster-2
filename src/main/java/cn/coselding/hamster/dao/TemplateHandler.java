package cn.coselding.hamster.dao;

import cn.coselding.hamster.domain.Article;
import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Freemarker模板引擎工具类
 * Created by 宇强 on 2016/3/13 0013.
 */
public class TemplateHandler implements ServletContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //模版文件夹配置，单例
    private Configuration configuration = null;
    private ServletContext context = null;

    @Override
    public void setServletContext(ServletContext servletContext) {
        context = servletContext;
        init();
    }

    public void init() {
        configuration = new Configuration();
        TemplateLoader templateLoader = new WebappTemplateLoader(context, "WEB-INF/ftl");
        configuration.setTemplateLoader(templateLoader);
        configuration.setDefaultEncoding("utf-8");
        configuration.setOutputEncoding("utf-8");
        logger.info("静态化处理器初始化成功...");
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    //向ftl模版中的数据替换
    public boolean parserTemplate(String ftlPath, Map<String, Object> map, OutputStream os) {
        try {
            Template template = getConfiguration().getTemplate(ftlPath, "UTF-8");
            template.process(map, new OutputStreamWriter(os, "UTF-8"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 全静态化页面,将数据库中查询到的文章数据静态化为一个html文件
     *
     * @param realRootPath 服务器根路径
     * @param params       模版参数
     * @return 返回合成的文章文件相对路径
     */
    public ArticlePath staticAllPage(String realRootPath, Map<String, Object> params) {
        Article article = (Article) params.get("article");
        //文章路径
        ArticlePath articlePath = getArticlePath(article);
        //文章文件绝对路径
        String filePath = realRootPath + articlePath.getFilePath();
        //文章文件的父文件夹路径
        String dirPath = realRootPath + "/article" + formatDate(article.getTime());
        try {
            //创建文件夹
            File dir = new File(dirPath);
            if (!dir.exists())
                dir.mkdirs();

            FileOutputStream fos = new FileOutputStream(filePath);
            parserTemplate("/article.ftl", params, fos);
            fos.close();
            logger.info("文章静态化成功：" + articlePath);
            return articlePath;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("/yyyy-MM-dd/");
        return format.format(date);
    }

    public ArticlePath getArticlePath(Article article) {
        return new ArticlePath("/article" + formatDate(article.getTime()) + article.getUrlTitle() + ".html",
                            "/article" + formatDate(article.getTime()) + article.getUrlTitle() + ".html"
        );
    }

    public String getArticleEncoderPath(Article article) {
        try {
            return "/article" + formatDate(article.getTime()) + URLEncoder.encode(article.getTitle(), "UTF-8") + ".html";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static class ArticlePath {
        private String filePath;
        private String urlPath;

        public ArticlePath(String filePath, String urlPath) {
            this.filePath = filePath;
            this.urlPath = urlPath;
        }

        public String getFilePath() {
            return filePath;
        }

        public String getUrlPath() {
            return urlPath;
        }

        @Override
        public String toString() {
            return "ArticlePath{" +
                    "filePath='" + filePath + '\'' +
                    ", urlPath='" + urlPath + '\'' +
                    '}';
        }
    }
}
