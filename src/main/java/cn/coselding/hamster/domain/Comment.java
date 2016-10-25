package cn.coselding.hamster.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 宇强 on 2016/3/11 0011.
 */
public class Comment {
    public static final int COMMENT_PASS=1;
    public static final int COMMENT_NO=2;
    public static final int COMMENT_WAIT=0;

    private int comid ;
    private String comcontent ;
    private int gid ;
    private int artid ;
    private Date comtime ;
    private String gname;
    private String comtimeshow;
    private int pass;

    public String getGemail() {
        return gemail;
    }

    public void setGemail(String gemail) {
        this.gemail = gemail;
    }

    private String gemail;

    public String getComtimeshow() {
        return this.comtimeshow;
    }

    public void setComtimeshow(String comtimeshow) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            this.comtime = format.parse(comtimeshow.replace("T"," "));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.comtimeshow = comtimeshow;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comid=" + comid +
                ", comcontent='" + comcontent + '\'' +
                ", gid=" + gid +
                ", artid=" + artid +
                ", comtime=" + comtime +
                ", gname='" + gname + '\'' +
                ", comtimeshow='" + comtimeshow + '\'' +
                ", pass=" + pass +
                ", gemail='" + gemail + '\'' +
                '}';
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public int getComid() {
        return comid;
    }

    public void setComid(int comid) {
        this.comid = comid;
    }

    public String getComcontent() {
        return comcontent;
    }

    public void setComcontent(String comcontent) {
        this.comcontent = comcontent;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getArtid() {
        return artid;
    }

    public void setArtid(int artid) {
        this.artid = artid;
    }

    public Date getComtime() {
        return comtime;
    }

    public void setComtime(Date comtime) {
        this.comtime = comtime;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        this.comtimeshow = format.format(this.comtime).replace(" ","T");
    }
}
