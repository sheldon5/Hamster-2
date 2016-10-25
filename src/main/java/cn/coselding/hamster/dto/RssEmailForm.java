package cn.coselding.hamster.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * Created by 宇强 on 2016/10/7 0007.
 */
public class RssEmailForm implements Serializable{

    @Length(min = 1,message = "{Length.rssEmailForm.gemail}")
    @Email(message = "{Email.rssEmailForm.gemail}")
    private String gemail ;

    @Override
    public String toString() {
        return "RssEmailForm{" +
                "gemail='" + gemail + '\'' +
                '}';
    }

    public String getGemail() {
        return gemail;
    }

    public void setGemail(String gemail) {
        this.gemail = gemail;
    }
}
