package cn.coselding.hamster.dao;

import cn.coselding.hamster.domain.Comment;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 宇强 on 2016/3/12 0012.
 */
public interface CommentDao {
    //增
    void saveComment(Comment comment);

    //删
    void deleteComnent(int comid);

    //改
    int updateComment(Comment comment);

    //查询单个
    Comment queryComment(int comid);

    void setCommentPass(@Param("comid") int comid,@Param("pass") int pass);

    //查询总数
    int queryCount();
    //查分页
    List<Comment> getPageData(@Param("startindex")int startindex, @Param("pagesize")int pagesize);

    int queryPassCount();
    List<Comment> getPassPageData(@Param("startindex")int startindex, @Param("pagesize")int pagesize);

    int queryWaitCount();
    List<Comment> getWaitPageData(@Param("startindex")int startindex, @Param("pagesize")int pagesize);

    //查询总数
    int queryGuestCount(int gid);

    //查分页
    List<Comment> getGuestPageData(@Param("gid")int gid, @Param("startindex")int startindex,@Param("pagesize")int pagesize);
}
