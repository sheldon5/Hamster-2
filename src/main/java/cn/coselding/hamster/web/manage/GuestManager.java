package cn.coselding.hamster.web.manage;

import cn.coselding.hamster.domain.Comment;
import cn.coselding.hamster.domain.Guest;
import cn.coselding.hamster.dto.GuestForm;
import cn.coselding.hamster.dto.Page;
import cn.coselding.hamster.dto.Query;
import cn.coselding.hamster.exception.ForeignKeyException;
import cn.coselding.hamster.filter.LoginFilter;
import cn.coselding.hamster.service.GuestService;
import cn.coselding.hamster.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**客户管理Controller
 * Created by 宇强 on 2016/10/4 0004.
 */
@Controller
@RequestMapping(value = "/manage/guest")
//user存在session中
@SessionAttributes(LoginFilter.LOGIN_TAG)
public class GuestManager implements ServletContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GuestService guestService;
    private String contextPath;
    private String realRootPath;

    @Override
    public void setServletContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
        realRootPath = servletContext.getRealPath("");
    }

    //添加客户界面
    @RequestMapping(value = "/add")
    public String add(Model model) {
        model.addAttribute("method", "add");
        model.addAttribute("pageTitle", "添加客户");
        return "manage/guest";
    }

    //添加客户操作
    @RequestMapping(value = "/add-go", method = RequestMethod.POST)
    public String addGo(@Valid GuestForm guestForm,
                        BindingResult result,
                        Model model) {
        if (result.hasErrors()) {
            WebUtils.validateForm(result, model);
            model.addAttribute("guest", guestForm);
            model.addAttribute("gid", guestForm.getGid());
            model.addAttribute("method", "add");
            model.addAttribute("pageTitle", "添加客户");
            return "manage/guest";
        }

        Guest guest = new Guest();
        WebUtils.copyBean(guestForm, guest);

        guestService.addGuest(guest);
        model.addAttribute("message", "客户添加成功！！！");
        model.addAttribute("url", contextPath + "/manage/guest/");
        logger.info("客户添加成功...：gname="+guestForm.getGname());
        return "message";
    }

    //删除客户操作
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam("gid") int gid,
                         Model model) {
        try {
            guestService.deleteGuest(gid);
            logger.info("客户删除成功...：gid="+gid);
        } catch (ForeignKeyException e) {
            model.addAttribute("message", "您要删除的客户下有留言，不能删除");
            model.addAttribute("url", contextPath + "/manage/guest/");
            logger.info("客户外键依赖，删除失败...：gid="+gid);
            return "message";
        }
        return "redirect:/manage/guest";
    }

    //修改客户操作
    @RequestMapping(value = "/update-go", method = RequestMethod.POST)
    public String updateGo(@Valid GuestForm guestForm,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            WebUtils.validateForm(result, model);
            model.addAttribute("guest", guestForm);
            model.addAttribute("gid", guestForm.getGid());
            model.addAttribute("method", "update");
            model.addAttribute("pageTitle", "修改客户");
            return "manage/guest";
        }

        Guest guest = new Guest();
        WebUtils.copyBean(guestForm, guest);

        guestService.updateGuest(guest);
        model.addAttribute("message", "客户信息修改成功！！！");
        model.addAttribute("url", contextPath + "/manage/guest/");
        logger.info("客户修改成功...：gname="+guestForm.getGname());
        return "message";
    }

    //修改客户界面
    @RequestMapping(value = "/update")
    public String update(@RequestParam("gid") int gid,
                         Model model) {
        GuestForm guestForm = new GuestForm();
        WebUtils.copyBean(guestService.queryGuest(gid), guestForm);
        model.addAttribute("guest", guestForm);
        model.addAttribute("gid", gid);

        model.addAttribute("method", "update");
        model.addAttribute("pageTitle", "修改客户");
        return "manage/guest";
    }

    //分页查询客户
    @RequestMapping(value = "/list")
    public String list(Query query,
                       Model model) {
        //分页查询所有客户
        String url = contextPath + "/manage/guest/";
        Page<Guest> page = guestService.queryPageGuests(query.pageNumValue(), url);
        model.addAttribute("page", page);
        return "manage/guest-manage";
    }

    @RequestMapping(value = "/comments")
    public String queryGuestComments(Query query,
                                     @RequestParam("gid") int gid,
                                     Model model) {
        //分页查询所有留言
        String url = contextPath + "/manage/guest/comments";
        Page<Comment> page = guestService.findGuestComments(query.pageNumValue(), url, gid);
        model.addAttribute("page", page);
        model.addAttribute("gid", gid);
        return "manage/comment-manage";
    }

    //默认情况下，查询客户列表
    @RequestMapping(value = "/")
    public String default1(Query query,Model model) {
        return list(query, model);
    }

    @RequestMapping(value = "")
    public String default2(Query query,Model model) {
        return list(query, model);
    }
}
