package cn.coselding.hamster.web.manage;

import cn.coselding.hamster.domain.Article;
import cn.coselding.hamster.domain.Category;
import cn.coselding.hamster.domain.User;
import cn.coselding.hamster.dto.ArticleForm;
import cn.coselding.hamster.dto.CommonMessage;
import cn.coselding.hamster.dto.Page;
import cn.coselding.hamster.dto.Query;
import cn.coselding.hamster.filter.LoginFilter;
import cn.coselding.hamster.service.ArticleService;
import cn.coselding.hamster.service.DraftService;
import cn.coselding.hamster.utils.Config;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 宇强 on 2016/10/11 0011.
 */
@Controller
@RequestMapping("/manage/draft")
@SessionAttributes({LoginFilter.LOGIN_TAG})
public class DraftManager implements ServletContextAware{

    public static final int LOAD_TYPE_CHANGE = 1;
    public static final String DRAFT_DRAFT = "DRAFT_DRAFT";
    public static final String DRAFT_EDITOR_TEMP = "DRAFT_EDITOR_TEMP";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DraftService draftService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private Markdown4jProcessor markdown4jProcessor;
    private String contextPath;
    @Autowired
    private Config config;

    @Override
    public void setServletContext(ServletContext servletContext) {
        contextPath=servletContext.getContextPath();
    }

    //添加文章界面
    @RequestMapping("/add")
    public String add(ArticleForm articleForm,
                      HttpSession session,
                      Model model) {
        //提取redirect参数缓存
        if(session.getAttribute(DRAFT_EDITOR_TEMP)!=null){
            articleForm = (ArticleForm) session.getAttribute(DRAFT_EDITOR_TEMP);
            session.removeAttribute(DRAFT_EDITOR_TEMP);
        }
        //草稿中没值，初始化
        if (session.getAttribute(DRAFT_DRAFT) == null) {
            //设置初始值
            articleForm.setDefault();
            session.setAttribute(DRAFT_DRAFT, articleForm);
        } else {
            //草稿中的值和当前添加文章匹配，加载草稿
            ArticleForm t = (ArticleForm) session.getAttribute(DRAFT_DRAFT);
            if (t.getArtid() == null || t.getArtid().length() == 0) {
                WebUtils.copyArticleInfo(t,articleForm);
                WebUtils.getDraft(DRAFT_DRAFT,session,articleForm,Integer.parseInt(articleForm.getEditor()));
                logger.info("add 加载草稿：" + articleForm);
            } else {//不匹配重新初始化
                //设置初始值
                articleForm.setDefault();
                session.setAttribute(DRAFT_DRAFT, articleForm);
            }
        }
        List<Category> categories = articleService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("method", "add");
        model.addAttribute("pageTitle", "添加草稿");
        model.addAttribute("draft", articleForm);
        return "manage/draft";
    }

    //添加文章操作
    @RequestMapping(value = "/add-go", method = RequestMethod.POST)
    public String addGo(@ModelAttribute(LoginFilter.LOGIN_TAG)User user,
                        @Valid ArticleForm articleForm,
                        BindingResult result,
                        HttpSession session,
                        Model model) {
        //表单校验
        if (result.hasErrors()) {
            WebUtils.validateForm(result, model);
            //数据回显
            model.addAttribute("categories", articleService.getAllCategories());
            model.addAttribute("draft", articleForm);
            model.addAttribute("method", "add");
            model.addAttribute("pageTitle", "添加草稿");
            return "manage/draft";
        }

        //加载session中的另一个编辑器文本
        if (session.getAttribute(DRAFT_DRAFT) != null) {
            WebUtils.getDraft(DRAFT_DRAFT,session, articleForm, (Integer.parseInt(articleForm.getEditor()) + 1) % 2);
            session.removeAttribute(DRAFT_DRAFT);
            logger.info("addGo加载完两编辑器：" + articleForm);
        }

        //封装草稿
        Article draft = new Article();
        WebUtils.copyBean(articleForm, draft);
        draft.setUid(user.getUid());
        draft.setAuthor(user.getUname());
        //初始化草稿信息
        WebUtils.initArticle(draft, WebUtils.getArticleMeta(draft,markdown4jProcessor));
        if(draft.getDeploy()==1) {
            //保存草稿
            draftService.addDraft(draft);
        } else {
            articleService.addArticle(draft,contextPath,config.getStaticArticlePath());
        }

        //删除草稿
        session.removeAttribute(DRAFT_DRAFT);
        model.addAttribute("message", "草稿录入成功！！！");
        model.addAttribute("url", contextPath + "/manage/draft/");
        logger.info("草稿添加成功...：title=" + draft.getTitle());
        return "message";
    }

    //修改文章界面
    @RequestMapping(value = "/update")
    public String update(ArticleForm articleForm,
                         HttpSession session,
                         Model model) {
        //提取redirect参数缓存
        if(session.getAttribute(DRAFT_EDITOR_TEMP)!=null){
            articleForm = (ArticleForm) session.getAttribute(DRAFT_EDITOR_TEMP);
            session.removeAttribute(DRAFT_EDITOR_TEMP);
        }
        //草稿中没值，初始化
        if (session.getAttribute(DRAFT_DRAFT) == null) {
            int artid = Integer.parseInt(articleForm.getArtid());
            //查询要修改的草稿
            Article draft = draftService.queryDraft(artid);
            WebUtils.copyBean(draft, articleForm);
            session.setAttribute(DRAFT_DRAFT, articleForm);
        } else {
            //草稿中的值和当前修改文章一致，直接加载草稿
            ArticleForm t = (ArticleForm) session.getAttribute(DRAFT_DRAFT);
            if (t.getArtid() != null && t.getArtid().equals(articleForm.getArtid())) {
                WebUtils.copyArticleInfo(t,articleForm);
                WebUtils.getDraft(DRAFT_DRAFT,session,articleForm,Integer.parseInt(articleForm.getEditor()));
                logger.info("update 加载草稿：" + articleForm);
            } else {
                //查询文章的其他数据
                int artid = Integer.parseInt(articleForm.getArtid());
                Article article = draftService.queryDraft(artid);
                WebUtils.copyBean(article, articleForm);
                session.setAttribute(DRAFT_DRAFT, articleForm);
            }
        }
        model.addAttribute("draft", articleForm);
        //查询所有类别
        List<Category> categories = articleService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("artid", articleForm.getArtid());
        model.addAttribute("method", "update");
        model.addAttribute("pageTitle", "编辑草稿");
        return "manage/draft";
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
            model.addAttribute("draft", articleForm);
            model.addAttribute("artid", articleForm.getArtid());
            model.addAttribute("method", "update");
            model.addAttribute("pageTitle", "编辑草稿");
            return "manage/draft";
        }

        //加载session中的另一个编辑器文本
        if (session.getAttribute(DRAFT_DRAFT) != null) {
            WebUtils.getDraft(DRAFT_DRAFT,session, articleForm, (Integer.parseInt(articleForm.getEditor()) + 1) % 2);
            session.removeAttribute(DRAFT_DRAFT);
            logger.info("updateGo加载完两编辑器：" + articleForm);
        }
        //封装草稿
        Article draft = new Article();
        WebUtils.copyBean(articleForm, draft);

        if(draft.getDeploy()==1) {
            //保存草稿
            draftService.updateDraft(draft);
        } else {
            articleService.updateArticle(draft,contextPath,config.getStaticArticlePath());
        }

        model.addAttribute("message", "草稿修改成功！！！");
        model.addAttribute("url", contextPath + "/manage/draft/");
        logger.info("草稿修改成功...：title=" + draft.getTitle());
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
        WebUtils.addToDraft(DRAFT_DRAFT,session, editor, articleForm, loadType, markdown4jProcessor);
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
        session.setAttribute(DRAFT_EDITOR_TEMP,articleForm);
        //根据不同的method跳转到不同的界面
        if (method != null && method.equals("add")) {
            return "redirect:/manage/draft/add";
        } else if (method != null && method.equals("update")) {
            return "redirect:/manage/draft/update?artid=" + articleForm.getArtid();
        }
        model.addAttribute("message", "您的操作参数不合法！");
        model.addAttribute("url", contextPath + "/manage/draft/");
        return "message";
    }

    //删除操作
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam("artid") int artid) {
        draftService.deleteDraft(artid);
        logger.info("草稿删除成功...artid=" + artid);
        return "redirect:/manage/draft/";
    }

    //删除操作
    @RequestMapping(value = "/deploy/{artid}")
    public String deploy(@PathVariable("artid") int artid) {
        Article draft = draftService.queryDraft(artid);
        draft.setDeploy(0);
        //发布还要静态化
        articleService.updateArticle(draft,contextPath,config.getStaticArticlePath());
        logger.info("草稿发布成功...artid=" + artid);
        return "redirect:/manage/draft/";
    }

    //分页查询草稿
    @RequestMapping(value = "/list")
    public String list(Query query,
                       Model model) {
        //分页查询所有博文
        String url = contextPath + "/manage/draft/";
        Page<Article> page = draftService.queryPage(query.pageNumValue(),url);
        model.addAttribute("page", page);
        return "manage/draft-manage";
    }

    @RequestMapping("/{artid}")
    public String queryById(@PathVariable("artid") int artid,
                                   Model model) {
        Article draft = draftService.queryDraft(artid);
        if (draft == null) {
            model.addAttribute("message", "草稿不存在");
            model.addAttribute("url", contextPath + "/");
            return "message";
        }
        model.addAttribute("draft",draft);
        model.addAttribute("categories",articleService.getAllCategories());
        model.addAttribute("method","update");
        model.addAttribute("artid",artid);
        model.addAttribute("pageTitle","编辑草稿");
        //找得到草稿，显示给浏览器
        return "manage/draft";
    }

    //草稿编辑页面重置
    @RequestMapping(value = "/reset",method = RequestMethod.POST)
    @ResponseBody
    public CommonMessage resetDraft(@RequestParam("artid")int artid,
                                    @RequestParam("editor")int editor){
        Article draft = draftService.queryDraft(artid);
        draft.setEditor(editor);
        Map<String,Object> res = new HashMap();
        res.put("article",draft);
        return CommonMessage.success(res);
    }

    //默认情况下，查询草稿列表
    @RequestMapping(value = "/")
    public String default1(Query query, Model model) {
        return list(query, model);
    }

    @RequestMapping(value = "")
    public String default2(Query query, Model model) {
        return list(query, model);
    }
}
