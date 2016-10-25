package cn.coselding.hamster.dto;

import java.io.Serializable;

/**
 * Created by 宇强 on 2016/10/6 0006.
 */
public class Query implements Serializable {

    private String pagenum;
    private String cid;
    private String key;

    public int pageNumValue(){
        if(pagenum==null||pagenum.length()<=0)return 1;
        try {
            int value = Integer.parseInt(pagenum);
            return value>0?value:1;
        }catch (Exception e){
            //默认查询第一页
            return 1;
        }
    }

    public int cidValue(){
        if(cid==null||cid.length()<=0)return -1;
        try {
            int value = Integer.parseInt(cid);
            return value>0?value:-1;
        }catch (Exception e){
            //默认查询第一页
            return -1;
        }
    }

    public String keyValue(){
        return key;
    }

    public String getPagenum() {
        return pagenum;
    }

    public void setPagenum(String pagenum) {
        this.pagenum = pagenum;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Query{" +
                "pagenum='" + pagenum + '\'' +
                ", cid='" + cid + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
