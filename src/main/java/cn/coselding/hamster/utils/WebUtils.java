package cn.coselding.hamster.utils;


import cn.coselding.hamster.domain.Article;
import cn.coselding.hamster.dto.ArticleForm;
import cn.coselding.hamster.dto.MarkDownImageResult;
import cn.coselding.hamster.dto.RegisterForm;
import cn.coselding.hamster.web.manage.ArticleManager;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.markdown4j.Markdown4jProcessor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Web层工具类
 *
 * @author 宇强 2016-3-12
 */
public class WebUtils {

    /**
     * 封装表单校验错误信息
     *
     * @param result
     * @param model
     * @return
     */
    public static Map<String, Object> validateForm(BindingResult result, Model model) {
        Map<String, Object> errors = new HashMap<String, Object>();
        for (ObjectError error : result.getAllErrors()) {
            //System.out.println(error);
            errors.put(((DefaultMessageSourceResolvable) error.getArguments()[0]).getDefaultMessage(), error.getDefaultMessage());
        }
        model.addAttribute("errors", errors);
        return errors;
    }

    /**
     * 校验确认密码
     *
     * @param registerForm
     * @param errors
     */
    public static boolean validatePassword2(RegisterForm registerForm, Map<String, Object> errors, Model model) {
        if (registerForm.getPassword() == null) {
            registerForm.setPassword("");
        }
        if (!registerForm.getPassword().equals(registerForm.getPassword2())) {
            if (errors == null) {
                errors = new HashMap<String, Object>();
                model.addAttribute("errors", errors);
            }
            errors.put("password2", "两次密码必须一致");
            return true;
        }
        return false;
    }

    /**
     * 初始化封装文章实体
     */
    public static Article initArticle(Article article, String meta) {
        if (article.getEditor() == Article.EDITOR_CK) {//ckeditor提取html的body
            chEditorInit(article);
        }
        //设置meta
        article.setMeta(meta);
        return article;
    }

    public static String getArticleMeta(Article article,Markdown4jProcessor processor) {
        if (article.getEditor() == Article.EDITOR_MD) {
            return getArticleMetaFromMD(article.getMd(),processor);
        } else if (article.getEditor() == Article.EDITOR_CK) {
            return getArticleMetaFromContent(article.getContent());
        } else return getArticleMetaFromMD(article.getMd(),processor);
    }

    /**
     * 从content提取摘要
     *
     * @param content
     * @return
     */
    public static String getArticleMetaFromContent(String content) {
        if(content==null)return "";
        //存html标签堆栈
        Stack<String> elements = new Stack<String>();
        //要提取的正文摘要
        StringBuilder meta = new StringBuilder();
        int i=0;
        //遍历字符串，限制摘要长度为220，标签堆栈，i标记当前到达的位置，结束时再i位置后面添加剩余缺少的尾标签
        for (; i < content.length()&&meta.length()<220; i++) {
            char c = content.charAt(i);
            if (c == '<') {//标签开头
                i++;
                if (content.charAt(i) == '/') {//尾标签
                    i++;
                    StringBuilder element = new StringBuilder();
                    while (content.charAt(i) != '>') {
                        element.append(content.charAt(i));
                        i++;
                    }
                    String start = elements.pop();
                    //由于开始标签可能有属性，因此startWith
                    while(!start.startsWith(element.toString())){//有些没有结束标签的略过
                        start = elements.pop();//将堆栈中的开始标签删除
                    }
                } else {//开始标签
                    StringBuilder element = new StringBuilder();
                    while ( content.charAt(i) != '>') {
                        element.append(content.charAt(i));
                        i++;
                    }
                    //开始标签堆栈
                    elements.push(element.toString());
                }
            } else {//非标签正文
                meta.append(c);
            }
        }
        //循环结束的0~i处就是我们要的
        meta = new StringBuilder(content.substring(0,i));
        meta.append("...");
        while(!elements.isEmpty()){//把标签堆栈中剩余的标签添加尾标签
            String tag = elements.pop();
            meta.append("</").append(tag).append('>');
        }
        return "<span style=\"font-family:微软雅黑;\">" + meta + "</span>";
    }

    /**
     * 从md提取meta
     *
     * @param md
     * @return
     */
    public static String getArticleMetaFromMD(String md,Markdown4jProcessor processor) {
        try {//先转换html再提取
            String html = processor.process(md);
            return getArticleMetaFromContent(html);
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
    }

    /**
     * 初始化用户提交的文章内容，提取html中的body
     *
     * @param article
     * @return
     */
    public static Article chEditorInit(Article article) {
        String content = article.getContent();
        //截取正文部分
        if (content != null && content.contains("<body>")) {
            content = content.substring(content.indexOf("<body>") + 6);
            content = content.substring(0, content.indexOf("</body>"));
            article.setContent(content);
        }
        return article;
    }

    /**把requestForm表单更新session中的基本信息和editor对应的位置
     * @param session
     * @param editor
     * @param requestForm
     * @param loadType
     * @param markdown4jProcessor
     */
    public static void addToDraft(String sessionTag,HttpSession session, int editor,ArticleForm requestForm, int loadType, Markdown4jProcessor markdown4jProcessor) {
        ArticleForm articleForm = (ArticleForm) session.getAttribute(sessionTag);
        if (articleForm == null) {
            articleForm = new ArticleForm();
            session.setAttribute(sessionTag, articleForm);
        }
        //复制其他表单信息
        copyArticleInfo(requestForm,articleForm);
        if (editor == Article.EDITOR_CK) {
            articleForm.setContent(requestForm.getContent());
            articleForm.setEditor(editor + "");
            if(loadType==ArticleManager.LOAD_TYPE_CHANGE){//CK的直接复制到MD
                articleForm.setMd(requestForm.getContent());
            }
        } else if (editor == Article.EDITOR_MD) {
            articleForm.setMd(requestForm.getMd());
            articleForm.setEditor(editor + "");
            if(loadType==ArticleManager.LOAD_TYPE_CHANGE){//MD转换之后复制到CK
                try {
                    articleForm.setContent(markdown4jProcessor.process(requestForm.getMd()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //复制文章表单的基本信息
    public static void copyArticleInfo(ArticleForm src, ArticleForm dest) {
        dest.setTitle(src.getTitle());
        dest.setArtid(src.getArtid());
        dest.setCid(src.getCid());
        dest.setDeploy(src.getDeploy());
        dest.setMeta(src.getMeta());
        dest.setShowtime(src.getShowtime());
        dest.setType(src.getType());
        dest.setTop(src.getTop());
    }

    /**把session中对应editor的非空值赋值给articleForm
     * @param session
     * @param articleForm
     * @param editor
     */
    public static void getDraft(String sessionTag,HttpSession session, ArticleForm articleForm, int editor) {
        ArticleForm t = (ArticleForm) session.getAttribute(sessionTag);
        if (editor == Article.EDITOR_MD && t.getMd() != null && t.getMd().length() > 0) {
            articleForm.setMd(t.getMd());
        } else if (editor == Article.EDITOR_CK && t.getContent() != null && t.getContent().length() > 0) {
            articleForm.setContent(t.getContent());
        }
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static String toDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 格式化日期和时分
     *
     * @param date
     * @return
     */
    public static String toDateTimeString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    /**
     * 将request中的数据封装到javabean中
     *
     * @param request
     * @param clazz   要合成的JavaBean类
     * @return 返回合成好的JavaBean对象
     */
    public static <T> T request2Bean(HttpServletRequest request, Class<T> clazz) {
        try {
            // 创建Bean
            T t = clazz.newInstance();
            // 将Request中的数据填充到Bean
            Enumeration enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String name = (String) enumeration.nextElement();
                String value = request.getParameter(name);
                BeanUtils.setProperty(t, name, value);
            }
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将一个javabean中的数据复制到另一种类的javabean，通过属性名对应
     *
     * @param src  源Bean
     * @param dest 目标Bean
     */
    public static void copyBean(Object src, Object dest) {

        // 设置Date类型的转换器
        ConvertUtils.register(new Converter() {

            public Object convert(Class type, Object value) {
                if (value == null)
                    return new Date();
                String str = (String) value;
                if (str.trim().equals("") || str.trim().equals("null"))
                    return new Date();
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    return format.parse(str);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, Date.class);
        try {
            BeanUtils.copyProperties(dest, src);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过文件名和UUID结合得到唯一的文件名
     *
     * @param filename 文件名
     * @return 返回UUID结合的唯一文件名
     */
    public static String encodeFilename(String filename) {
        return getUUID() + "_" + filename.substring(filename.lastIndexOf("."));
    }

    /**
     * 通过哈希打散文件名找到文件路径
     *
     * @param filename
     * @param savePath
     * @return
     */
    public static String encodePath(String filename, String savePath) {

        int hashCode = filename.hashCode();
        int dir1 = hashCode & 0xf;
        int dir2 = (hashCode & 0xf0) >> 4;
        String path = savePath + "/" + dir1 + "/" + dir2;
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return dir1 + "/" + dir2 + "/" + filename;
    }

    /**
     * 获取一个UUID字符串
     *
     * @return返回的UUID字符串
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
