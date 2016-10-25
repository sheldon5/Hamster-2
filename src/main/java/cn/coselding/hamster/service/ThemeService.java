package cn.coselding.hamster.service;

import cn.coselding.hamster.dto.Theme;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by 宇强 on 2016/10/24 0024.
 */
public interface ThemeService {
    int ADD = 1;
    int CHANGE = 0;

    //递归复制文件夹
    void copyDir(File srcFile, File destFile);

    //删除主题
    ThemeService deleteTheme(String name);

    //查询所有主题
    List<Theme> queryAllThemes(File parent);

    //保存添加主题
    void saveTheme(String name, MultipartFile logo);

    //加载更换主题
    void loadTheme(String name);
}
