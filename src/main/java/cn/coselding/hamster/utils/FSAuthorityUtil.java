package cn.coselding.hamster.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 文件系统权限设置工具类
 * Created by coselding on 16/12/9.
 */
public class FSAuthorityUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Config config;

    public FSAuthorityUtil(Config config) {
        this.config = config;
    }

    public void passAuthority() {
        try {
            Runtime.getRuntime().exec(config.getFsAuthCommand() + " " + config.getUploadImagePath());
            Runtime.getRuntime().exec(config.getFsAuthCommand() + " " + config.getStaticArticlePath());
            Runtime.getRuntime().exec(config.getFsAuthCommand() + " " + config.getStaticIndexPath());
            logger.info("rumtime exec success...");
        } catch (IOException e) {
            logger.error("rumtime exec error...");
        }
    }

}
