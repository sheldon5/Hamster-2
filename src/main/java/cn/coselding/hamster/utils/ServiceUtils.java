package cn.coselding.hamster.utils;

import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Service层工具类
 *
 * @author 宇强 2015-11-05
 */
public class ServiceUtils {

    /**
     * 创建List副本，防止线程冲突
     *
     * @param list  要复制的List
     * @param clazz List的泛型类
     * @return 返回List的副本
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> List<T> copyList(List<T> list, Class<T> clazz) {
        try {
            List<T> result = new ArrayList<T>();
            for (T t : list) {
                T temp = clazz.newInstance();
                BeanUtils.copyProperties(temp, t);
                result.add(temp);
            }
            return result;
        } catch (Exception e) {
            return new ArrayList<T>();
        }
    }

    /**
     * 对字符串进行md5加密
     *
     * @param message 要加密的字符串
     * @return 加密完成的字符串
     */
    public static String md5(String message) {
        if (message == null) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte md5[] = digest.digest(message.getBytes());

            Base64 base64 = new Base64();
            return base64.encodeToString(md5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

    /**
     * 复制Bean
     *
     * @param src   要复制的Bean
     * @param clazz Bean的类型
     * @return 返回Bean的副本
     */
    public static <T> T cloneBean(Object src, Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            BeanUtils.copyProperties(t, src);
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String removeHtml(String content) {
        if(content==null)return null;
        if (content.contains("<html")) {
//            int start = content.indexOf("<body>")+6;
//            int end = content.indexOf("</body>");
//            return content.substring(start,end);
            Pattern p = Pattern.compile("<body>([\\s\\S]*)</body>");
            Matcher matcher = p.matcher(content);
            if (matcher.find()) {
                System.out.println("found!!!");
                return matcher.group(1);
            } else
                throw new RuntimeException("文章内容格式有误！！！");
        } else
            return content;
    }

    public static String staticImageSize(String html) {
        if (html == null) return null;
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("img");
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            //img自有的width和height
            element.attr("width", "100%");
            element.attr("height", "");

            //css样式
            //删除width
            StringBuilder style = new StringBuilder(element.attr("style"));
            int widthStart = style.indexOf("width");
            int widthEnd = widthStart+5;
            while(widthEnd<style.length()&&style.charAt(widthEnd)!=';'){
                widthEnd++;
            }
            if(widthStart>=0) {
                style.delete(widthStart, widthEnd);
            }
            //删除height
            int heightStart = style.indexOf("height");
            int heightEnd = heightStart+6;
            while(heightEnd<style.length()&&style.charAt(heightEnd)!=';'){
                heightEnd++;
            }
            if(heightStart>=0) {
                style.delete(heightStart, heightEnd);
            }
            //设置css
            element.attr("style",style.toString());
        }
        return document.body().html();
    }
}
