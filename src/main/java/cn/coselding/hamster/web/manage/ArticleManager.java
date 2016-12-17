package cn.coselding.hamster.web.manage;

import cn.coselding.hamster.dao.DraftDao;
import cn.coselding.hamster.dao.TemplateHandler;
import cn.coselding.hamster.domain.Article;
import cn.coselding.hamster.domain.Category;
import cn.coselding.hamster.domain.User;
import cn.coselding.hamster.dto.*;
import cn.coselding.hamster.filter.LoginFilter;
import cn.coselding.hamster.service.ArticleService;
import cn.coselding.hamster.utils.Config;
import cn.coselding.hamster.utils.FSAuthorityUtil;
import cn.coselding.hamster.utils.WebUtils;
import org.markdown4j.Markdown4jProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件管理Controller
 * Created by 宇强 on 2016/10/4 0004.
 */
@Controller
@RequestMapping(value = "/manage/article")
//user,article(ArticleForm)存在session中
@SessionAttributes({LoginFilter.LOGIN_TAG})
public class ArticleManager implements ServletContextAware {
    //草稿的键
    public static final String ARTICLE_DRAFT = "articleDraft";
    public static final String EDITOR_TEMP = "EDITOR_TEMP";
    public static final int LOAD_TYPE_CHANGE = 1;
    public static final int LOAD_TYPE_NO = 0;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArticleService articleService;
    @Autowired
    private TemplateHandler templateHandler;
    @Autowired
    private Markdown4jProcessor markdown4jProcessor;
    @Autowired
    private Config config;
    @Autowired
    private FSAuthorityUtil fsAuthorityUtil;
    private String contextPath;
//    private String realRootPath;

    @Override
    public void setServletContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
//        realRootPath = servletContext.getRealPath("/");
    }

    //添加文章界面
    @RequestMapping(value = "/add")
    public String add(ArticleForm articleForm,
                      HttpSession session,
                      Model model) {
        //提取redirect参数缓存
        if(session.getAttribute(EDITOR_TEMP)!=null){
            articleForm = (ArticleForm) session.getAttribute(EDITOR_TEMP);
            session.removeAttribute(EDITOR_TEMP);
        }
        //草稿中没值，初始化
        if (session.getAttribute(ARTICLE_DRAFT) == null) {
            //设置初始值
            articleForm.setDefault();
            session.setAttribute(ARTICLE_DRAFT, articleForm);
        } else {
            //草稿中的值和当前添加文章匹配，加载草稿
            ArticleForm t = (ArticleForm) session.getAttribute(ARTICLE_DRAFT);
            if (t.getArtid() == null || t.getArtid().length() == 0) {
                //如果session保存了草稿，加载草稿文本
                WebUtils.copyArticleInfo(t,articleForm);
                WebUtils.getDraft(ARTICLE_DRAFT,session,articleForm,Integer.parseInt(articleForm.getEditor()));
                logger.info("add加载草稿：" + articleForm);
            } else {//不匹配重新初始化
                //设置初始值
                articleForm.setDefault();
                session.setAttribute(ARTICLE_DRAFT, articleForm);
            }
        }
        List<Category> categories = articleService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("method", "add");
        model.addAttribute("pageTitle", "添加文章");
        model.addAttribute("article", articleForm);
        return "manage/article";
    }

    //添加文章操作
    @RequestMapping(value = "/add-go", method = RequestMethod.POST)
    public String addGo(@ModelAttribute(LoginFilter.LOGIN_TAG) User user,
                        @Valid ArticleForm articleForm,
                        BindingResult result,
                        HttpSession session,
                        Model model) {
        //表单校验
        if (result.hasErrors()) {
            WebUtils.validateForm(result, model);

            //数据回显
            model.addAttribute("categories", articleService.getAllCategories());
            model.addAttribute("article", articleForm);
            model.addAttribute("method", "add");
            model.addAttribute("pageTitle", "添加文章");
            return "manage/article";
        }

        //加载session中的另一个编辑器文本
        if (session.getAttribute(ARTICLE_DRAFT) != null) {
            WebUtils.getDraft(ArticleManager.ARTICLE_DRAFT,session, articleForm, (Integer.parseInt(articleForm.getEditor()) + 1) % 2);
            session.removeAttribute(ARTICLE_DRAFT);
            logger.info("addGo加载完两编辑器：" + articleForm);
        }

        //封装博文体
        Article article = new Article();
        WebUtils.copyBean(articleForm, article);
        article.setUid(user.getUid());
        article.setAuthor(user.getUname());
        //初始化文章信息
        WebUtils.initArticle(article, WebUtils.getArticleMeta(article,markdown4jProcessor));
        //保存博文
        articleService.addArticle(article, contextPath, config.getStaticArticlePath());

        //删除草稿
        session.removeAttribute(ARTICLE_DRAFT);
        model.addAttribute("message", "博文录入成功！！！");
        model.addAttribute("url", contextPath + "/manage/article/");
        logger.info("文章添加成功...：title=" + article.getTitle());

        fsAuthorityUtil.passAuthority();
        return "message";
    }

    //修改文章界面
    @RequestMapping(value = "/update")
    public String update(ArticleForm articleForm,
                         HttpSession session,
                         Model model) {
        //提取redirect参数缓存
        if(session.getAttribute(EDITOR_TEMP)!=null){
            articleForm = (ArticleForm) session.getAttribute(EDITOR_TEMP);
            session.removeAttribute(EDITOR_TEMP);
        }
        //草稿中没值，初始化
        if (session.getAttribute(ARTICLE_DRAFT) == null) {
            int artid = Integer.parseInt(articleForm.getArtid());
            //查询要修改的文章
            Article article = articleService.queryArticle(artid, true);
            WebUtils.copyBean(article, articleForm);
            session.setAttribute(ARTICLE_DRAFT, articleForm);
        } else {
            //草稿中的值和当前修改文章一致，直接加载草稿
            ArticleForm t = (ArticleForm) session.getAttribute(ARTICLE_DRAFT);
            if (t.getArtid() != null && t.getArtid().equals(articleForm.getArtid())) {
                //加载草稿中
                WebUtils.copyArticleInfo(t,articleForm);
                WebUtils.getDraft(ARTICLE_DRAFT,session,articleForm,Integer.parseInt(articleForm.getEditor()));
                logger.info("update加载草稿：" + articleForm);
            } else {
                //草稿和当前文章不匹配，重新初始化
                int artid = Integer.parseInt(articleForm.getArtid());
                //查询要修改的文章
                Article article = articleService.queryArticle(artid, true);
                WebUtils.copyBean(article, articleForm);
                session.setAttribute(ARTICLE_DRAFT, articleForm);
            }

        }
        model.addAttribute("article", articleForm);
        //查询所有类别
        List<Category> categories = articleService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("artid", articleForm.getArtid());
        model.addAttribute("method", "update");
        model.addAttribute("pageTitle", "修改文章");
        return "manage/article";
    }

    //修改文章操作
    @RequestMapping(value = "/update-go", method = RequestMethod.POST)
    public String updateGo(@Valid ArticleForm articleForm,
                           BindingResult result,
                           HttpSession session,
                           Model model) {
        if (result.hasErrors()) {
            WebUtils.validateForm(result, model);
            model.addAttribute("categories", articleService.getAllCategories());
            model.addAttribute("article", articleForm);
            model.addAttribute("artid", articleForm.getArtid());
            model.addAttribute("md", articleForm.getMd());
            model.addAttribute("method", "update");
            model.addAttribute("pageTitle", "修改文章");
            return "manage/article";
        }

        //加载session中的另一个编辑器文本
        if (session.getAttribute(ARTICLE_DRAFT) != null) {
            WebUtils.getDraft(ArticleManager.ARTICLE_DRAFT,session, articleForm, (Integer.parseInt(articleForm.getEditor()) + 1) % 2);
            session.removeAttribute(ARTICLE_DRAFT);
            logger.info("updateGo加载完两编辑器：" + articleForm);
        }
        //封装博文体
        Article article = new Article();
        WebUtils.copyBean(articleForm, article);

        System.out.println("updateGo-->>"+article);

        //修改博文
        articleService.updateArticle(article, contextPath, config.getStaticArticlePath());

        model.addAttribute("message", "博文修改成功！！！");
        model.addAttribute("url", contextPath + "/manage/article/");
        logger.info("文章修改成功...：title=" + article.getTitle());

        fsAuthorityUtil.passAuthority();
        return "message";
    }

    //改变编辑器
    @RequestMapping("/editor")
    public String editor(ArticleForm articleForm,
                         @RequestParam("method") String method,
                         @RequestParam(value = "loadType") int loadType,
                         HttpSession session,
                         Model model) {
        //把之前的文本保存到session草稿
        int editor = (Integer.parseInt(articleForm.getEditor()) + 1) % 2;
        //修改session中的值
        WebUtils.addToDraft(ArticleManager.ARTICLE_DRAFT,session, editor, articleForm, loadType, markdown4jProcessor);
        //修改request中的值
        if (loadType == LOAD_TYPE_CHANGE) {//表示将其中一个编辑器中的文本转换复制到另一个编辑器
            if (editor == Article.EDITOR_CK) {//CK的直接复制到MD
                articleForm.setMd(articleForm.getContent());
            } else if (editor == Article.EDITOR_MD) {//MD转换之后复制到CK
                try {
                    articleForm.setContent(markdown4jProcessor.process(articleForm.getMd()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //redirect参数缓存
        session.setAttribute(EDITOR_TEMP,articleForm);
        //根据不同的method跳转到不同的界面
        if (method != null && method.equals("add")) {
            return "redirect:/manage/article/add";
        } else if (method != null && method.equals("update")) {
            return "redirect:/manage/article/update?artid="+articleForm.getArtid();
        }
        model.addAttribute("message", "您的操作参数不合法！");
        model.addAttribute("url", contextPath + "/manage/article/");
        return "message";
    }

    //删除操作
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam("artid") int artid,
                         Model model) {
        articleService.deleteArticle(artid, config.getStaticArticlePath());
        logger.info("文章删除成功...artid=" + artid);
        return "redirect:/manage/article/";
    }

    //ajax改变编辑器,只是个demo
    @RequestMapping("/editor/ajax")
    @ResponseBody
    public Map<String, Object> editor(@RequestParam("md") String md,
                                      @RequestParam("src") int src,
                                      @RequestParam("dest") int dest) {
        System.out.println("md=" + md + "  src=" + src + "  dest=" + dest);
        Map<String, Object> res = new HashMap<String, Object>();
        if (src == 1 && dest == 0) {
            res.put("state", 1);
            res.put("content", md);
        } else if (src == 0 && dest == 1) {
            res.put("state", 1);
            res.put("content", md);
        } else {
            res.put("state", 0);
            res.put("message", "提交信息不合法");
        }
        return res;
    }

    //分页查询文章
    @RequestMapping(value = "/list")
    public String list(Query query,
                       Model model) {
        //分页查询所有博文
        String url = contextPath + "/manage/article/";
        Page<Article> page = articleService.getPageArticles(query.pageNumValue(), url);
        model.addAttribute("page", page);
        return "manage/article-manage";
    }

    //查看单篇文章
    @RequestMapping(value = "/{year}-{month}-{day}/{title}/", method = RequestMethod.GET)
    public String queryArticle(@PathVariable("title") String title,
                               @PathVariable("year") String year,
                               @PathVariable("month") String month,
                               @PathVariable("day") String day,
                               Model model) {
        String path = "/article/" + year + "/" + month + "/" + day + "/";
        File html = new File(config.getStaticArticlePath() + path + title + ".html");
        if (!html.exists()) {
            model.addAttribute("message", "文章不存在");
            model.addAttribute("url", contextPath + "/");
            return "message";
        }
        //找得到文章，显示给浏览器
        return "forward:" + path + title + ".html";
    }

    @RequestMapping("/{artid}")
    public String queryArticleById(@PathVariable("artid") int artid,
                                   Model model) {
        Article article = articleService.queryArticle(artid, false);
        if (article == null) {
            model.addAttribute("message", "文章不存在");
            model.addAttribute("url", contextPath + "/");
            return "message";
        }

        TemplateHandler.ArticlePath path = templateHandler.getArticlePath(article);
        File html = new File(config.getStaticArticlePath() + path.getFilePath());
        if (!html.exists()) {
            model.addAttribute("message", "文章不存在");
            model.addAttribute("url", contextPath + "/");
            return "message";
        }
        //找得到文章，显示给浏览器
        return "forward:" + path.getUrlPath();
    }

    //重新静态化文章
    @RequestMapping(value = "/reload/{artid}", method = RequestMethod.GET)
    @ResponseBody
    public CommonMessage reload(@PathVariable("artid") int artid) {
        articleService.reloadArticle(artid, contextPath, config.getStaticArticlePath());
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("message", "博文重新静态化成功！！！");
        logger.info("文章静态化成功...：artid=" + artid);
        return CommonMessage.success(res);
    }

    //静态化所有文章
    @RequestMapping(value = "/reload/all", method = RequestMethod.GET)
    @ResponseBody
    public CommonMessage reloadAll() {
        articleService.reloadAllArticles(contextPath, config.getStaticArticlePath());
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("message", "所有博文重新静态化成功！！！");
        logger.info("全部文章静态化成功...");
        return CommonMessage.success(res);
    }

    //静态化主页
    @RequestMapping(value = "/reload/index", method = RequestMethod.GET)
    @ResponseBody
    public CommonMessage reloadIndex() {
        articleService.staticIndex(contextPath, config.getStaticIndexPath());
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("message", "主页静态化成功！！！");
        logger.info("主页静态化成功...");
        return CommonMessage.success(res);
    }

    //数据库格式化
    @RequestMapping(value = "/formatdb", method = RequestMethod.GET)
    @ResponseBody
    public CommonMessage formatdb() {
        articleService.formatFromDB();
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("message", "数据库格式化成功！！！");
        logger.info("数据库内容标准格式化成功...");
        return CommonMessage.success(res);
    }

    //文章编辑页面重置
    @RequestMapping(value = "/reset",method = RequestMethod.POST)
    @ResponseBody
    public CommonMessage resetArticle(@RequestParam("artid")int artid,
                               @RequestParam("editor")int editor){
        Article article = articleService.queryArticle(artid,true);
        article.setEditor(editor);
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("article", article);
        return CommonMessage.success(res);
    }

    //默认情况下，查询文章列表
    @RequestMapping(value = "/")
    public String default1(Query query, Model model) {
        return list(query, model);
    }

    @RequestMapping(value = "")
    public String default2(Query query, Model model) {
        return list(query, model);
    }
}
