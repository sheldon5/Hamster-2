package cn.coselding.hamster.domain;

import java.util.Date;

/**
 * Created by 宇强 on 2016/3/11 0011.
 */
public class Category {
    private int cid ;
    private String cname ;
    private int count;
    private Date ctime ;

    @Override
    public String toString() {
        return "Category{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                ", count=" + count +
                ", ctime=" + ctime +
                '}';
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
