package cn.coselding.hamster.web.manage;

import cn.coselding.hamster.filter.LoginFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**管理系统主界面Controller
 * Created by 宇强 on 2016/10/4 0004.
 */
@Controller
@RequestMapping("/manage")
//user存在session中
@SessionAttributes(LoginFilter.LOGIN_TAG)
public class MainManager {

    public String default1(Model model) {
        return "manage/main";
    }

    @RequestMapping("/")
    public String index2(Model model) {
        return default1(model);
    }

    @RequestMapping("/index")
    public String index3(Model model) {
        return default1(model);
    }

    @RequestMapping("/index/")
    public String index4(Model model) {
        return default1(model);
    }
}
