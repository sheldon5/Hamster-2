package cn.coselding.hamster.web;

import cn.coselding.hamster.domain.Comment;
import cn.coselding.hamster.domain.Guest;
import cn.coselding.hamster.dto.Page;
import cn.coselding.hamster.dto.Query;
import cn.coselding.hamster.dto.VisitorCommentForm;
import cn.coselding.hamster.service.ArticleService;
import cn.coselding.hamster.service.VisitorService;
import cn.coselding.hamster.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**访客留言Controller
 * Created by 宇强 on 2016/10/4 0004.
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController implements ServletContextAware{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VisitorService visitorService;
    @Autowired
    private ArticleService articleService;
    private String contextPath;

    @Override
    public void setServletContext(ServletContext servletContext) {
        contextPath=servletContext.getContextPath();
    }

    //表单校验
    private String validateUI(String artid, String title) {
        //在artid不是数字的情况下title没意义，赋值空
        try {
            if (artid == null || artid.trim().equals(""))
                title = null;
            else
                Integer.parseInt(artid);
        } catch (Exception e) {
            title = null;
        }
        return title;
    }

    //默认url
    @RequestMapping(value = "")
    public String commentUI2(VisitorCommentForm visitorCommentForm,
                            Query query,
                            Model model){
        return commentUI(visitorCommentForm,query,model);
    }
    //提供访客留言界面
    @RequestMapping(value = "/")
    public String commentUI(VisitorCommentForm visitorCommentForm,
                            Query query,
                            Model model) {
        String url = contextPath + "/comment/";
        Page<Comment> page = visitorService.findComments(query.pageNumValue(), url);

        //把用户的邮箱隐藏起来，安全作用，尊重用户
        for (Comment comment : page.getList()) {
            String email = comment.getGemail().substring(0, 2) + "******" + comment.getGemail().substring(comment.getGemail().lastIndexOf("@") - 1);
            comment.setGemail(email);
        }
        model.addAttribute("categories", articleService.getAllCategories());
        model.addAttribute("page", page);
        model.addAttribute("title", visitorCommentForm.getTitle());
        model.addAttribute("artid", visitorCommentForm.getArtid());
        return "comment";
    }

    //访客留言操作
    @RequestMapping(value = "/submit")
    @ResponseBody
    public Map<String,Object> comment(@Valid VisitorCommentForm visitorCommentForm,
                          BindingResult result) {
        Map<String,Object> res = new HashMap<String,Object>();
        //表单校验失败
        if(result.hasErrors()){
            //数据回显
            for (ObjectError error : result.getAllErrors()) {
                res.put("state",0);
                res.put("message",error.getDefaultMessage());
                return res;
            }
        }

        //表单校验成功
        Guest guest = new Guest();
        WebUtils.copyBean(visitorCommentForm,guest);
        //添加访客记录
        guest = visitorService.addGuest(guest);
        logger.info("访客记录添加成功...：gname="+guest.getGname());

        //查看title和artid的校验情况
        String title = validateUI(visitorCommentForm.getArtid(),visitorCommentForm.getTitle());

        //封装留言
        Comment comment = new Comment();
        comment.setGemail(guest.getGemail());
        comment.setGname(guest.getGname());
        comment.setComcontent(visitorCommentForm.getComcontent());
        comment.setComtime(new Date());
        comment.setArtid(title==null?0:Integer.parseInt(visitorCommentForm.getArtid()));
        comment.setGid(guest.getGid());

        //添加留言记录
        visitorService.addComment(guest, comment, contextPath);
        logger.info("留言添加成功...：comcontent="+comment.getComcontent());

        res.put("state",1);
        res.put("message","留言添加成功，待您的留言通过审核，将会对您的邮箱进行保护并显示在留言列表，谢谢合作！");
        return res;
    }
}
