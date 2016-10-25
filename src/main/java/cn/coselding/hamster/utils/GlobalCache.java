package cn.coselding.hamster.utils;

import cn.coselding.hamster.domain.Category;
import cn.coselding.hamster.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 宇强 on 2016/3/12 0012.
 */
public class GlobalCache {
    //订阅缓存
    public static final int RSS_TRUE = 1;
    public static final int RSS_FALSE = 0;
    private Config config;

    public GlobalCache(Config config) {
        this.config = config;
    }

    //类别
    private boolean categories_cached = false;
    public boolean containsCategories() {
        if (categories == null || categories.size() <= 0) {
            categories_cached=false;
        }
        return categories_cached;
    }

    private List<Category> categories = new ArrayList<Category>();
    public List<Category> getCategories() {
        return categories;
    }
    public void setCategories(List<Category> categories) {
        this.categories = categories;
        this.categories_cached = config.isGlobalCacheEnable();
    }
}
