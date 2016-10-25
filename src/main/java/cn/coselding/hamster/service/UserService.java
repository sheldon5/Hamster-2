package cn.coselding.hamster.service;

import cn.coselding.hamster.dto.Page;
import cn.coselding.hamster.domain.User;
import cn.coselding.hamster.exception.ForeignKeyException;
import cn.coselding.hamster.exception.UserExistException;
import org.springframework.transaction.annotation.Transactional;

/**管理员：处理管理员帐号业务逻辑
 * Created by 宇强 on 2016/10/4 0004.
 */
public interface UserService {
    //用户登录
    User login(String uname, String password);

    //用户注册
    @Transactional
    void register(String uname, String password) throws UserExistException;

    //删除用户
    void deleteUser(int uid) throws ForeignKeyException;

    //更新用户信息
    @Transactional
    void updateUser(User user);

    //查询指定用户
    User queryUser(int uid);

    //分页查询用户
    @Transactional
    Page<User> queryPageUsers(int pagenum, String url);
}
