package cn.coselding.hamster.dao;


import cn.coselding.hamster.domain.User;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 宇强 on 2016/3/12 0012.
 */
public interface UserDao {
    //增
    void saveUser(User user);

    //删
    int deleteUser(int uid);

    //改
    int updateUser(User user);

    //查询单个
    User queryUser(int uid);

    //查询单个
    User queryUserByName(String uname);

    //查询总数
    int getCount();

    //查询多个
    List<User> queryUsers();

    //查询分页
    List<User> getPageData(@Param("startindex") int startindex, @Param("pagesize") int pagesize);
}
