package cn.coselding.hamster.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by 宇强 on 2016/10/6 0006.
 */
public class CategoryForm implements Serializable{

    @Length(min = 1,message = "{Length.categoryForm.cname}")
    private String cname;
    private String cid;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "CategoryForm{" +
                "cname='" + cname + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}
