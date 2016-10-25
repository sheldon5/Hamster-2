package cn.coselding.hamster.dao;

import cn.coselding.hamster.domain.Guest;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by 宇强 on 2016/3/12 0012.
 */
public interface GuestDao {
    //增
    int saveGuest(Guest guest);

    //删
    int deleteGuest(int gid);

    //改
    int updateGuest(Guest guest);

    //查询单个
    Guest queryGuest(int gid);

    //查询单个
    Guest queryGuestByEmail(String email);

    //查询分页
    List<Guest> getPageData(@Param("startindex") int startindex,@Param("pagesize") int pagesize);

    //查总数
    int queryCount();

    List<Guest> queryRssGuests();
}
