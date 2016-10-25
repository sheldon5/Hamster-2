package cn.coselding.hamster.service.impl;

import cn.coselding.hamster.dao.UserDao;
import cn.coselding.hamster.dto.Page;
import cn.coselding.hamster.domain.User;
import cn.coselding.hamster.exception.ForeignKeyException;
import cn.coselding.hamster.exception.UserExistException;
import cn.coselding.hamster.utils.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**管理员：处理管理员帐号业务逻辑
 * Created by 宇强 on 2016/3/13 0013.
 */
@Service
public class UserServiceImpl implements cn.coselding.hamster.service.UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserDao userDao;

    //用户登录
    public User login(String uname, String password) {
        password = ServiceUtils.md5(password);
        User user = userDao.queryUserByName(uname);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    //用户注册
    @Transactional
    public void register(String uname, String password) throws UserExistException {
        password = ServiceUtils.md5(password);
        User user = userDao.queryUserByName(uname);
        if (user != null) {
            logger.info("用户已存在");
            throw new UserExistException();
        }
        User myUser = new User();
        myUser.setUname(uname);
        myUser.setPassword(password);
        myUser.setUtime(new Date());
        userDao.saveUser(myUser);
    }

    //删除用户
    public void deleteUser(int uid) throws ForeignKeyException {
        int res = userDao.deleteUser(uid);
        if (res == 0) {
            logger.info("用户删除失败");
            throw new ForeignKeyException("用户删除失败");
        }
    }

    //更新用户信息
    @Transactional
    public void updateUser(User user) {
        User t = userDao.queryUser(user.getUid());
        t.setPassword(ServiceUtils.md5(user.getPassword()));
        t.setUname(user.getUname());
        userDao.updateUser(t);
    }

    //查询指定用户
    public User queryUser(int uid) {
        return userDao.queryUser(uid);
    }

    //分页查询用户
    @Transactional
    public Page<User> queryPageUsers(int pagenum, String url) {
        // 总记录数
        int totalrecord = (int) userDao.getCount();
        // 根据传递的页号查找所需显示数据
        Page<User> page = new Page<User>(totalrecord, pagenum);
        List<User> list = userDao.getPageData(page.getStartindex(),
                page.getPagesize());
        page.setList(list);
        page.setUrl(url);

        return page;
    }
}
