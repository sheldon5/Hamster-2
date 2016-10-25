package cn.coselding.hamster.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 宇强 on 2016/10/6 0006.
 */
public class CommentForm implements Serializable{

    private String comid ;
    @Length(min = 1,message = "{Length.commentForm.comcontent}")
    private String comcontent ;
    private String gid ;
    private String artid ;
    private String gname;
    @Pattern(regexp = "[0-9]{1,4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}",message = "{Pattern.commentForm.comtimeshow}")
    private String comtimeshow;

    public String getComid() {
        return comid;
    }

    public void setComid(String comid) {
        this.comid = comid;
    }

    public String getComcontent() {
        return comcontent;
    }

    public void setComcontent(String comcontent) {
        this.comcontent = comcontent;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getArtid() {
        return artid;
    }

    public void setArtid(String artid) {
        this.artid = artid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getComtimeshow() {
        return comtimeshow;
    }

    public void setComtimeshow(String comtimeshow) {
        this.comtimeshow = comtimeshow;
    }

    @Override
    public String toString() {
        return "CommentForm{" +
                "comid='" + comid + '\'' +
                ", comcontent='" + comcontent + '\'' +
                ", gid='" + gid + '\'' +
                ", artid='" + artid + '\'' +
                ", gname='" + gname + '\'' +
                ", comtimeshow='" + comtimeshow + '\'' +
                '}';
    }
}
