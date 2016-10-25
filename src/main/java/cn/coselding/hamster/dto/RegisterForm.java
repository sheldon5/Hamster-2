package cn.coselding.hamster.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by 宇强 on 2016/10/6 0006.
 */
public class RegisterForm implements Serializable{

    private String uid;
    @Length(min = 1,message = "{Length.registerForm.uname}")
    @Pattern(regexp = "[a-zA-Z0-9]{6,30}",message = "{Pattern.registerForm.uname}")
    private String uname;
    @Length(min = 1,message = "{Length.registerForm.password}")
    @Pattern(regexp = "[a-zA-Z0-9]{8,30}",message = "{Pattern.registerForm.password}")
    private String password;
    @Length(min = 1,message = "{Length.registerForm.password2}")
    @Pattern(regexp = "[a-zA-Z0-9]{8,30}",message = "{Pattern.registerForm.password2}")
    private String password2;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    @Override
    public String toString() {
        return "RegisterForm{" +
                "uid='" + uid + '\'' +
                ", uname='" + uname + '\'' +
                ", password='" + password + '\'' +
                ", password2='" + password2 + '\'' +
                '}';
    }
}
