package cn.coselding.hamster.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 宇强 on 2016/10/6 0006.
 */
public class ArticleForm implements Serializable {

    @Length(min = 1, message = "{Length.articleForm.title}")
    private String title;
    @Length(min = 1, message = "{Length.articleForm.type}")
    private String type;
    private String artid;
    private String cid;
    @Length(min = 1, message = "{Length.articleForm.content}")
    private String content;
    @Pattern(regexp = "[0-9]{1,4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}", message = "{Pattern.articleForm.showtime}")
    private String showtime;
    @Length(min = 1, message = "{Length.articleForm.content}")
    private String md;
    @Pattern(regexp = "[0-9]+", message = "{Pattern.articleForm.editor}")
    private String editor;
    private String top;
    private String meta;
    private String deploy;
    @Length(min = 1, message = "{Length.articleForm.urlTitle}")
    private String urlTitle;

    public ArticleForm() {
        setDefault();
    }

    public void setDefault() {
        type = "原创";
        showtime = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").format(new Date());
        top = "0";
        editor = "0";
        deploy = "1";
        title = null;
        content = null;
        artid = null;
        cid = null;
        md = null;
        meta = null;
        urlTitle = null;
    }

    public String getDeploy() {
        return deploy;
    }

    public void setDeploy(String deploy) {
        this.deploy = deploy;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArtid() {
        return artid;
    }

    public void setArtid(String artid) {
        this.artid = artid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getUrlTitle() {
        return urlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        this.urlTitle = urlTitle;
    }

    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", artid='" + artid + '\'' +
                ", cid='" + cid + '\'' +
                ", content='" + content + '\'' +
                ", showtime='" + showtime + '\'' +
                ", md='" + md + '\'' +
                ", editor='" + editor + '\'' +
                ", top='" + top + '\'' +
                ", meta='" + meta + '\'' +
                ", deploy='" + deploy + '\'' +
                ", urlTitle='" + urlTitle + '\'' +
                '}';
    }

    public String serialParams() {
        try {
            return "title=" + (title == null ? "" : URLEncoder.encode(title, "UTF-8")) +
                    "&type=" + (type == null ? "" : URLEncoder.encode(type, "UTF-8")) +
                    "&artid=" + artid +
                    "&cid=" + cid +
                    "&content=" + (content == null ? "" : URLEncoder.encode(content, "UTF-8")) +
                    "&showtime=" + showtime +
                    "&md=" + (md == null ? "" : URLEncoder.encode(md, "UTF-8")) +
                    "&editor=" + editor +
                    "&top=" + top +
                    "&deploy=" + deploy +
                    "&meta=" + (meta == null ? "" : URLEncoder.encode(meta, "UTF-8")) +
                    "&urlTitle=" + (urlTitle == null ? "" : urlTitle);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
