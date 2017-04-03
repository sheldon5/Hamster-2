package cn.coselding.hamster.service.impl;

import cn.coselding.hamster.dao.CommentDao;
import cn.coselding.hamster.dao.GuestDao;
import cn.coselding.hamster.domain.Comment;
import cn.coselding.hamster.domain.Guest;
import cn.coselding.hamster.dto.Page;
import cn.coselding.hamster.exception.ForeignKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理员：处理访客和留言板业务逻辑
 * Created by 宇强 on 2016/3/15 0015.
 */
@Service
public class GuestServiceImpl implements cn.coselding.hamster.service.GuestService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GuestDao guestDao;
    @Autowired
    private CommentDao commentDao;

    //查询指定客户
    public Guest queryGuest(int gid) {
        return guestDao.queryGuest(gid);
    }

    //删除客户
    public void deleteGuest(int gid) throws ForeignKeyException {
        int res = guestDao.deleteGuest(gid);
        if (res == 0)
            throw new ForeignKeyException("访客删除失败");
    }

    //更新客户信息
    public void updateGuest(Guest guest) {
        guestDao.updateGuest(guest);
    }

    //添加客户
    @Transactional
    public void addGuest(Guest guest) {
        Guest temp = guestDao.queryGuestByEmail(guest.getGemail());
        if (temp != null) {
            temp.setRss(guest.getRss());
            temp.setGemail(guest.getGemail());
            temp.setGname(guest.getGname());
            guestDao.updateGuest(temp);
        } else
            guestDao.saveGuest(guest);
    }

    //分页查询客户
    @Transactional
    public Page<Guest> queryPageGuests(int pagenum, String url) {
        // 总记录数
        int totalrecord = (int) guestDao.queryCount();
        // 根据传递的页号查找所需显示数据
        Page<Guest> page = new Page<Guest>(totalrecord, pagenum);
        List<Guest> list = guestDao.getPageData(page.getStartindex(),
                page.getPagesize());
        page.setList(list);
        page.setUrl(url);

        return page;
    }

    //添加留言
    public void addComment(Comment comment) {
        commentDao.saveComment(comment);
    }

    //删除留言
    public void deleteComment(int comid) {
        commentDao.deleteComnent(comid);
    }

    //修改留言信息
    public void updateComment(Comment comment) {
        commentDao.updateComment(comment);
    }

    public void setCommentPass(int comid, int pass) {
        commentDao.setCommentPass(comid, pass);
    }

    @Override
    public void checkAllComments(int pass) {
        commentDao.checkAllComments(pass);
    }


    //查询指定留言
    public Comment queryComment(int comid) {
        return commentDao.queryComment(comid);
    }

    //分页查询留言
    @Transactional
    public Page<Comment> queryPageComments(int pagenum, String url) {
        // 总记录数
        int totalrecord = (int) commentDao.queryCount();
        // 根据传递的页号查找所需显示数据
        Page<Comment> page = new Page<Comment>(totalrecord, pagenum);
        List<Comment> list = commentDao.getPageData(page.getStartindex(),
                page.getPagesize());
        page.setList(list);
        page.setUrl(url);
        return page;
    }

    //分页查询留言
    @Transactional
    public Page<Comment> queryWaitPageComments(int pagenum, String url) {
        // 总记录数
        int totalrecord = (int) commentDao.queryWaitCount();
        // 根据传递的页号查找所需显示数据
        Page<Comment> page = new Page<Comment>(totalrecord, pagenum);
        List<Comment> list = commentDao.getWaitPageData(page.getStartindex(),
                page.getPagesize());
        page.setList(list);
        page.setUrl(url);
        return page;
    }

    //分页查询指定客户的留言
    @Transactional
    public Page<Comment> findGuestComments(int pagenum, String url, int gid) {
        // 总记录数
        int totalrecord = (int) commentDao.queryGuestCount(gid);
        // 根据传递的页号查找所需显示数据
        Page<Comment> page = new Page<Comment>(totalrecord, pagenum);
        List<Comment> list = commentDao.getGuestPageData(gid, page.getStartindex(),
                page.getPagesize());
        page.setList(list);
        page.setUrl(url);

        return page;
    }
}
