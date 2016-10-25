package cn.coselding.hamster.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by 宇强 on 2016/10/6 0006.
 */
public class GuestForm implements Serializable{

    private String gid ;
    @Length(min = 1,message = "{Length.guestForm.gname}")
    private String gname ;
    @Length(min = 1,message = "{Length.guestForm.gemail}")
    @Email(message = "{Email.guestForm.gemail}")
    private String gemail;
    private String rss;

    @Override
    public String toString() {
        return "GuestForm{" +
                "gid='" + gid + '\'' +
                ", gname='" + gname + '\'' +
                ", gemail='" + gemail + '\'' +
                ", rss.jsp='" + rss + '\'' +
                '}';
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGemail() {
        return gemail;
    }

    public void setGemail(String gemail) {
        this.gemail = gemail;
    }

    public String getRss() {
        return rss;
    }

    public void setRss(String rss) {
        this.rss = rss;
    }
}
