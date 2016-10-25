package cn.coselding.hamster.service;

import cn.coselding.hamster.domain.Comment;
import cn.coselding.hamster.domain.Guest;
import cn.coselding.hamster.dto.Page;
import cn.coselding.hamster.exception.ForeignKeyException;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理员：处理访客和留言板业务逻辑
 * Created by 宇强 on 2016/10/4 0004.
 */
public interface GuestService {
    //查询指定客户
    Guest queryGuest(int gid);

    //删除客户
    void deleteGuest(int gid) throws ForeignKeyException;

    //更新客户信息
    void updateGuest(Guest guest);

    //添加客户
    @Transactional
    void addGuest(Guest guest);

    //分页查询客户
    @Transactional
    Page<Guest> queryPageGuests(int pagenum, String url);

    //添加留言
    void addComment(Comment comment);

    //删除留言
    void deleteComment(int comid);

    //修改留言信息
    void updateComment(Comment comment);

    //设置留言审核情况
    void setCommentPass(int comid, int pass);

    //查询指定留言
    Comment queryComment(int comid);

    Page<Comment> queryPageComments(int pagenum, String url);

    Page<Comment> queryWaitPageComments(int pagenum, String url);

    //分页查询指定客户的留言
    @Transactional
    Page<Comment> findGuestComments(int pagenum, String url, int gid);
}
