package cn.coselding.hamster.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by 宇强 on 2016/10/6 0006.
 */
public class VisitorCommentForm implements Serializable{

    @Length(min = 1,message = "{Length.visitorCommentForm.gemail}")
    @Email(message = "{Email.visitorCommentForm.gemail}")
    private String gemail ;
    @Length(min = 1,message = "{Length.visitorCommentForm.comcontent}")
    private String comcontent ;
    @Length(min = 1,message = "{Length.visitorCommentForm.gname}")
    private String gname;
    private String title;
    private String artid;

    private String showtime;

    @Override
    public String toString() {
        return "VisitorCommentForm{" +
                "gemail='" + gemail + '\'' +
                ", comcontent='" + comcontent + '\'' +
                ", gname='" + gname + '\'' +
                ", title='" + title + '\'' +
                ", artid='" + artid + '\'' +
                ", showtime='" + showtime + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtid() {
        return artid;
    }

    public void setArtid(String artid) {
        this.artid = artid;
    }

    public String getGemail() {
        return gemail;
    }

    public void setGemail(String gemail) {
        this.gemail = gemail;
    }

    public String getComcontent() {
        return comcontent;
    }

    public void setComcontent(String comcontent) {
        this.comcontent = comcontent;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }
}
