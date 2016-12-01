package cn.coselding.hamster.utils;

import cn.coselding.hamster.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置信息工具类
 * Created by 宇强 on 2016/3/16 0016.
 */
public class Config {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Properties prop = null;
    private boolean indexDynamic = false;
    private String solrUrl;
    private String serverHost;
    private boolean userRegister = false;
    private String userEmail;
    private boolean modeDev=false;
    private String uploadUrlImageCkeditor;
    private boolean globalCacheEnable=false;
    private String uploadImagePath;

    public Config(String congifFilename) {
        InputStream is = Config.class.getClassLoader().getResourceAsStream(congifFilename);
        prop = new Properties();
        try {
            prop.load(is);
            indexDynamic = Boolean.parseBoolean(prop.getProperty("index.dynamic"));
            solrUrl = prop.getProperty("solr.url");
            serverHost = prop.getProperty("server.host");
            userRegister = Boolean.parseBoolean(prop.getProperty("user.register"));
            userEmail = prop.getProperty("user.email");
            modeDev = Boolean.parseBoolean(prop.getProperty("mode.dev"));
            uploadUrlImageCkeditor=prop.getProperty("uploadUrl.image.ckeditor");
            globalCacheEnable=Boolean.parseBoolean(prop.getProperty("global.cache.enable"));
            uploadImagePath=prop.getProperty("upload.image.path");
            prop.clear();
            prop = null;
            logger.info("全局缓存初始化成功...");
        } catch (IOException e) {
            logger.error(e.getMessage()+" 全局缓存配置文件不存在",e);
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                is = null;
            }
        }
    }

    public boolean isGlobalCacheEnable() {
        return globalCacheEnable;
    }

    public String getUploadUrlImageCkeditor() {
        return uploadUrlImageCkeditor;
    }

    public boolean isModeDev() {
        return modeDev;
    }

    public boolean isIndexDynamic() {
        return indexDynamic;
    }

    public String getSolrUrl() {
        return solrUrl;
    }

    public String getServerHost() {
        return serverHost;
    }

    public boolean isUserRegister() {
        return userRegister;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUploadImagePath() {
        return uploadImagePath;
    }
}
