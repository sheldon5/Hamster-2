package cn.coselding.hamster.service.impl;

import cn.coselding.hamster.dto.Theme;
import cn.coselding.hamster.service.ThemeService;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletContext;
import java.io.*;
import java.util.*;

/**
 * 主题处理器
 * Created by 宇强 on 2016/10/23 0023.
 */
@Service
public class ThemeServiceImpl implements ServletContextAware, cn.coselding.hamster.service.ThemeService {

    //配置文件中的信息
    private Map<String, String> prop = new HashMap<String, String>();
    private String themeName;
    private String realRootPath;
    private String contextPath;

    @Override
    public void setServletContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
        realRootPath = servletContext.getRealPath("/");
    }

    //初始化
    private ThemeServiceImpl initProp(String themeName, File file) {
        this.themeName = themeName;
        //file文件的配置信息覆盖
        if (file.exists()) {
            Properties p = new Properties();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                p.load(fis);
                //加载配置文件中的配置信息
                prop.put("theme.dir.ftl", (String) p.getOrDefault("theme.dir.ftl", "/WEB-INF/ftl"));
                prop.put("theme.dir.jsp", (String) p.getOrDefault("theme.dir.jsp", "/WEB-INF/jsp"));
                prop.put("theme.dir.css", (String) p.getOrDefault("theme.dir.css", "/css"));
                prop.put("theme.dir.js", (String) p.getOrDefault("theme.dir.js", "/js"));
                prop.put("theme.dir.fonts", (String) p.getOrDefault("theme.dir.fonts", "/fonts"));
                prop.put("theme.dir.images", (String) p.getOrDefault("theme.dir.images", "/images"));
                prop.put("theme.dir.public", (String) p.getOrDefault("theme.dir.public", "/public"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                        fis = null;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return this;
    }

    //默认配置
    private ThemeServiceImpl defaultProp() {
        //默认配置
        prop.clear();
        prop.put("theme.dir.ftl", "/WEB-INF/ftl");
        prop.put("theme.dir.jsp", "/WEB-INF/jsp");
        prop.put("theme.dir.css", "/css");
        prop.put("theme.dir.js", "/js");
        prop.put("theme.dir.fonts", "/fonts");
        prop.put("theme.dir.images", "/images");
        prop.put("theme.dir.public", "/public");
        return this;
    }

    //复制ftl模版文件
    private ThemeServiceImpl copyFtl(int flag) {
        String src = realRootPath + "/WEB-INF/themes/" + themeName + prop.get("theme.dir.ftl");
        String dest = realRootPath + "/WEB-INF/ftl";
        //复制主页模版
        if (flag == ADD) {
            copyFile("index.ftl", new File(dest), new File(src));
        } else {
            copyFile("index.ftl", new File(src), new File(dest));
        }
        //复制文章模版
        if (flag == ADD) {
            copyFile("article.ftl", new File(dest), new File(src));
        } else {
            copyFile("article.ftl", new File(src), new File(dest));
        }
        return this;
    }

    //复制jsp文件
    private ThemeServiceImpl copyJsp(int flag) {
        File src = new File(realRootPath + "/WEB-INF/themes/" + themeName + prop.get("theme.dir.jsp"));
        File dest = new File(realRootPath + "/WEB-INF/jsp");
        //复制comment.jsp
        if (flag == ADD) {
            copyFile("comment.jsp", dest, src);
        } else {
            copyFile("comment.jsp", src, dest);
        }
        //复制error.jsp
        if (flag == ADD) {
            copyFile("error.jsp", dest, src);
        } else {
            copyFile("error.jsp", src, dest);
        }
        //复制index.jsp
        if (flag == ADD) {
            copyFile("index.jsp", dest, src);
        } else {
            copyFile("index.jsp", src, dest);
        }
        //复制list.jsp
        if (flag == ADD) {
            copyFile("list.jsp", dest, src);
        } else {
            copyFile("list.jsp", src, dest);
        }
        //复制message.jsp
        if (flag == ADD) {
            copyFile("message.jsp", dest, src);
        } else {
            copyFile("message.jsp", src, dest);
        }
        //复制rss.jsp
        if (flag == ADD) {
            copyFile("rss.jsp", dest, src);
        } else {
            copyFile("rss.jsp", src, dest);
        }
        return this;
    }

    //复制css
    private ThemeServiceImpl copyCss(int flag) {
        String src = realRootPath + "/WEB-INF/themes/" + themeName + prop.get("theme.dir.css");
        String dest = realRootPath + "/css";
        File srcFile = new File(src);
        File destFile = new File(dest);
        if (flag == ADD) {
            copyDir(destFile, srcFile);
        } else {
            copyDir(srcFile, destFile);
        }
        return this;
    }

    //复制js
    private ThemeServiceImpl copyJs(int flag) {
        String src = realRootPath + "/WEB-INF/themes/" + themeName + prop.get("theme.dir.js");
        String dest = realRootPath + "/js";
        File srcFile = new File(src);
        File destFile = new File(dest);
        if (flag == ADD) {
            copyDir(destFile, srcFile);
        } else {
            copyDir(srcFile, destFile);
        }
        return this;
    }

    //复制图片
    private ThemeServiceImpl copyIamges(int flag) {
        String src = realRootPath + "/WEB-INF/themes/" + themeName + prop.get("theme.dir.images");
        String dest = realRootPath + "/images";
        File srcFile = new File(src);
        File destFile = new File(dest);
        if (flag == ADD) {
            copyDir(destFile, srcFile);
        } else {
            copyDir(srcFile, destFile);
        }
        return this;
    }

    //复制字体
    private ThemeServiceImpl copyFonts(int flag) {
        String src = realRootPath + "/WEB-INF/themes/" + themeName + prop.get("theme.dir.fonts");
        String dest = realRootPath + "/fonts";
        File srcFile = new File(src);
        File destFile = new File(dest);
        if (flag == ADD) {
            copyDir(destFile, srcFile);
        } else {
            copyDir(srcFile, destFile);
        }
        return this;
    }

    //复制public
    private ThemeService copyPublic(int flag) {
        String src = realRootPath + "/WEB-INF/themes/" + themeName + prop.get("theme.dir.public");
        String dest = realRootPath + "/public";
        File srcFile = new File(src);
        File destFile = new File(dest);
        if (flag == ADD) {
            copyDir(destFile, srcFile);
        } else {
            copyDir(srcFile, destFile);
        }
        return this;
    }

    //递归复制文件夹
    @Override
    public void copyDir(File srcFile, File destFile) {
        File[] files = srcFile.listFiles();
        if(files!=null&&files.length>0) {
            for (File file : files) {
                if (file.isFile()) {
                    //复制文件
                    copyFile(file, destFile);
                } else {
                    //创建文件夹
                    String dirName = file.getName();
                    File dir = new File(destFile.getAbsolutePath() + "/" + dirName);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    //复制该文件夹下的文件
                    copyDir(file, dir);
                }
            }
        }
    }

    //复制文件到dir目录下
    private void copyFile(File file, File dir) {
        copyFile(file.getName(), file.getParentFile(), dir);
    }

    //复制filename文件从src下到dest下
    private void copyFile(String filename, File src, File dest) {
        if (!dest.exists()) {
            dest.mkdirs();
        }
        File destFile = new File(dest.getAbsolutePath() + "/" + filename);
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(destFile);
            fis = new FileInputStream(src.getAbsolutePath() + "/" + filename);
            IOUtils.copy(fis, fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                    fis = null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //删除主题
    @Override
    public ThemeService deleteTheme(String name) {
        deleteDir(new File(realRootPath + "/WEB-INF/themes/" + name));
        return this;
    }

    //递归删除主题文件夹
    private void deleteDir(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteDir(f);
            }
        }
        file.delete();
    }

    //查询所有主题
    @Override
    public List<Theme> queryAllThemes(File parent) {
        File[] files = parent.listFiles();
        File dir = new File(realRootPath + "/upload/themes/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        List<Theme> themes = new ArrayList<Theme>();
        for (File th : files) {
            if (th.isDirectory()) {
                Theme theme = new Theme();
                //复制logo图片
                File img = new File(th.getAbsolutePath() + "/default.jpg");
                if (img.exists()) {
                    copyFile(img, new File(dir.getAbsolutePath() + "/" + th.getName()));
                    theme.setLogo(contextPath + "/upload/themes/" + th.getName() + "/default.jpg");
                } else {
                    theme.setLogo(contextPath + "/upload/themes/default.jpg");
                }
                theme.setName(th.getName());
                theme.setPath(th.getAbsolutePath());
                themes.add(theme);
            }
        }
        return themes;
    }

    //保存添加主题
    @Override
    public void saveTheme(String name, MultipartFile logo) {
        defaultProp().addTheme(name)
                .saveLogo(logo)
                .copyFtl(ADD)
                .copyJsp(ADD)
                .copyCss(ADD)
                .copyJs(ADD)
                .copyFonts(ADD)
                .copyIamges(ADD)
                .copyPublic(ADD);
    }

    //保存图标
    private ThemeServiceImpl saveLogo(MultipartFile logo) {
        File logoFile = new File(realRootPath + "/WEB-INF/themes/" + themeName + "/default.jpg");
        try {
            if (logo != null) {
                logo.transferTo(logoFile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    //保存主题初始化，创建主题文件夹，配置文件等
    private ThemeServiceImpl addTheme(String name) {
        this.themeName = name;
        File theme = new File(realRootPath + "/WEB-INF/themes/" + name);
        if (!theme.exists()) {
            theme.mkdirs();
        }
        File prop = new File(theme.getAbsolutePath() + "/theme.properties");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(prop);
            Properties properties = new Properties();
            for (Map.Entry<String, String> entry : this.prop.entrySet()) {
                properties.put(entry.getKey(), entry.getValue());
            }
            properties.store(fos, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return this;
    }

    //加载更换主题
    @Override
    public void loadTheme(String name) {
        defaultProp().initProp(name, new File(realRootPath + "/WEB-INF/themes/" + name + "/theme.properties"))
                .copyFtl(CHANGE)
                .copyJsp(CHANGE)
                .copyCss(CHANGE)
                .copyJs(CHANGE)
                .copyFonts(CHANGE)
                .copyIamges(CHANGE)
                .copyPublic(CHANGE);
    }
}
