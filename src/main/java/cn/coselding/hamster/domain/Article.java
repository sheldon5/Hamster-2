package cn.coselding.hamster.domain;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by 宇强 on 2016/3/11 0011.
 */
public class Article {

    public static final int EDITOR_MD = 0;
    public static final int EDITOR_CK = 1;

    private int artid;
    private int cid;
    private Date time;
    private String author;
    private int likes;
    private int looked;
    private String title;
    private String meta;
    private String content;
    private String staticURL;
    private int uid;
    private String type;
    private String showtime;
    private int top = 0;
    private String cname;
    private String md;
    private int editor;
    private int deploy;
    private String urlTitle;

    public int getEditor() {
        return editor;
    }

    public void setEditor(int editor) {
        this.editor = editor;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public String staticPath() {
        return getCid() + "/" + getCid() + "-" + artid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getShowtime() {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        //this.showtime = format.format(time).replace(" ","T");
        return this.showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            this.time = format.parse(showtime.replace("T", " "));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDeploy() {
        return deploy;
    }

    public void setDeploy(int deploy) {
        this.deploy = deploy;
    }

    public String getUrlTitle() {
        return urlTitle;
    }

    @Override
    public String toString() {
        return "Article{" +
                "artid=" + artid +
                ", cid=" + cid +
                ", time=" + time +
                ", author='" + author + '\'' +
                ", likes=" + likes +
                ", looked=" + looked +
                ", title='" + title + '\'' +
                ", meta='" + meta + '\'' +
                ", content='" + content + '\'' +
                ", staticURL='" + staticURL + '\'' +
                ", uid=" + uid +
                ", type='" + type + '\'' +
                ", showtime='" + showtime + '\'' +
                ", top=" + top +
                ", cname='" + cname + '\'' +
                ", md='" + md + '\'' +
                ", editor=" + editor +
                ", deploy=" + deploy +
                ", urlTitle='" + urlTitle + '\'' +
                '}';
    }

    public void setUrlTitle(String urlTitle) {
        this.urlTitle = urlTitle;
    }

    public int getArtid() {
        return artid;
    }

    public void setArtid(int artid) {
        this.artid = artid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.showtime = format.format(time).replace(" ", "T");
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getLooked() {
        return looked;
    }

    public void setLooked(int looked) {
        this.looked = looked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStaticURL() {
        return staticURL;
    }

    public void setStaticURL(String staticURL) {
        this.staticURL = staticURL;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
