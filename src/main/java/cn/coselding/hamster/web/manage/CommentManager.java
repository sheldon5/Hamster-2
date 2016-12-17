package cn.coselding.hamster.web.manage;

import cn.coselding.hamster.domain.Comment;
import cn.coselding.hamster.dto.CommentForm;
import cn.coselding.hamster.dto.CommonMessage;
import cn.coselding.hamster.dto.Page;
import cn.coselding.hamster.dto.Query;
import cn.coselding.hamster.filter.LoginFilter;
import cn.coselding.hamster.service.GuestService;
import cn.coselding.hamster.service.VisitorService;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**留言管理Controller
 * Created by 宇强 on 2016/10/4 0004.
 */
@Controller
@RequestMapping(value = "/manage/comment")
//user存在session中
@SessionAttributes(LoginFilter.LOGIN_TAG)
public class CommentManager implements ServletContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GuestService guestService;
    private String contextPath;

    @Override
    public void setServletContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
    }

    //添加留言界面
    @RequestMapping(value = "/add")
    public String add(Model model) {
        model.addAttribute("method", "add");
        model.addAttribute("pageTitle", "添加留言");
        return "manage/comment";
    }

    //添加留言操作
    @RequestMapping(value = "/add-go", method = RequestMethod.POST)
    public String addGo(@Valid CommentForm commentForm,
                        BindingResult result,
                        Model model) {
        if(result.hasErrors()){
            WebUtils.validateForm(result,model);
            model.addAttribute("comment", commentForm);
            model.addAttribute("comid", commentForm.getComid());
            model.addAttribute("method", "add");
            model.addAttribute("pageTitle", "添加留言");
            return "manage/comment";
        }

        Comment comment = new Comment();
        WebUtils.copyBean(commentForm,comment);

        guestService.addComment(comment);
        model.addAttribute("message", "留言添加成功！！！");
        model.addAttribute("url", contextPath + "/manage/comment/");
        logger.info("留言添加成功...comcontent="+commentForm.getComcontent());
        return "message";
    }

    //删除留言操作
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam("comid") int comid,
                         Model model) {
        guestService.deleteComment(comid);
        logger.info("留言删除成功...：comid="+comid);
        return "redirect:/manage/comment/";
    }

    //修改留言操作
    @RequestMapping(value = "/update-go", method = RequestMethod.POST)
    public String updateGo(@Valid CommentForm commentForm,
                           BindingResult result,
                           Model model) {
        if(result.hasErrors()){
            WebUtils.validateForm(result,model);
            model.addAttribute("comment", commentForm);
            model.addAttribute("comid", commentForm.getComid());
            model.addAttribute("method", "update");
            model.addAttribute("pageTitle", "修改留言");
            return "manage/comment";
        }

        Comment comment = new Comment();
        WebUtils.copyBean(commentForm,comment);

        guestService.updateComment(comment);
        model.addAttribute("message", "留言信息修改成功！！！");
        model.addAttribute("url", contextPath + "/manage/comment/");
        logger.info("留言修改成功...：comcontent="+commentForm.getComcontent());
        return "message";
    }

    //修改留言界面
    @RequestMapping(value = "/update")
    public String update(@RequestParam("comid") int comid,
                         Model model) {
        CommentForm commentForm = new CommentForm();
        WebUtils.copyBean(guestService.queryComment(comid),commentForm);
        model.addAttribute("comment", commentForm);
        model.addAttribute("comid", comid);

        model.addAttribute("method", "update");
        model.addAttribute("pageTitle", "修改留言");
        return "manage/comment";
    }

    @RequestMapping("/pass")
    @ResponseBody
    public CommonMessage passComment(@RequestParam("comid")int comid,
                                          @RequestParam("pass")int pass){
        Map<String,Object> res = new HashMap<String,Object>();
        guestService.setCommentPass(comid,pass);
        res.put("state",1);
        res.put("message","留言审核成功！");
        if(pass==1){
            res.put("resMsg","通过");
        }else {
            res.put("resMsg","不通过");
        }
        return CommonMessage.success(res);
    }

    @RequestMapping("/wait")
    public String waitComments(Query query,
                               Model model) {
        //分页查询所有留言
        String url = contextPath + "/manage/comment/wait";
        Page<Comment> page = guestService.queryWaitPageComments(query.pageNumValue(), url);
        for(Comment c:page.getList()){
            if(c.getComcontent().length()>20){
                c.setComcontent(c.getComcontent().substring(0,20)+"...");
            }
        }
        model.addAttribute("page", page);
        model.addAttribute("pageTitle", "待审核留言");
        return "manage/comment-manage";
    }

    //分页查询留言界面
    @RequestMapping(value = "/list")
    public String list(Query query,
                       Model model) {
        //分页查询所有留言
        String url = contextPath + "/manage/comment/";
        Page<Comment> page = guestService.queryPageComments(query.pageNumValue(), url);
        for(Comment c:page.getList()){
            if(c.getComcontent().length()>20){
                c.setComcontent(c.getComcontent().substring(0,20)+"...");
            }
        }
        model.addAttribute("page", page);
        model.addAttribute("pageTitle", "留言管理");
        return "manage/comment-manage";
    }

    //查询单个留言
    @RequestMapping(value = "/query")
    @ResponseBody
    public CommonMessage list(@RequestParam("comid")int comid) {
        Comment comment = guestService.queryComment(comid);
        System.out.println(comment);
        return CommonMessage.success(comment);
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
