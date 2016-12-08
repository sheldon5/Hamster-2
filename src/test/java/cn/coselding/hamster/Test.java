package cn.coselding.hamster;


import cn.coselding.hamster.dao.TemplateHandler;
import cn.coselding.hamster.domain.Article;
import cn.coselding.hamster.utils.ServiceUtils;
import freemarker.template.SimpleDate;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.markdown4j.Markdown4jProcessor;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

/**
 * Created by 宇强 on 2016/3/14 0014.
 */
public class Test {

    /**
     * 从content提取摘要
     *
     * @param article
     * @return
     */
    public static String getArticleMetaFromContent(Article article) {
        String content = article.getContent();
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
                        i=i+1;
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
        meta.append("... ");
        while(!elements.isEmpty()){//把标签堆栈中剩余的标签添加尾标签
            String tag = elements.pop();
            meta.append("</").append(tag).append('>');
        }
        return "<span style=\"font-family:微软雅黑;\">" + meta + "</span>";
    }

    public static String getMeta(String content) {
        Document document = Jsoup.parse(content);
        System.out.println(document.nodeName());
        System.out.println(document.getElementsByTag("h1").text());
        return "";
    }

    public static void getMetaTest() throws IOException {
        Article article = new Article();
        article.setContent("<h1>我的标题</h1>\n" +
                "<blockquote><ul>\n" +
                "<li>样例1\n" +
                "<br  />样例2</li>\n" +
                "<li>hahaha\n" +
                "<br  />啦啦啦</li>\n" +
                "</ul>\n" +
                "</blockquote>\n" +
                "<p><img src=\"/upload/images/0/5/83466ee8-76b0-4aea-a029-16723f50a1fe_.jpg\" alt=\"\" /></p>\n" +
                "<p>![](/upload/images/0/5/83466ee8-76b0-4aea-a029-16723f50a1fe_.jpg =100)</p>\n" +
                "<p><img src=\" /upload/images/0/5/83466ee8-76b0-4aea-a029-16723f50a1fe_.jpg\" width=\"100%\"/></p>\n" +
                "<pre><code>&lt;img src=\"/upload/images/0/5/83466ee8-76b0-4aea-a029-16723f50a1fe_.jpg\"/&gt;\n" +
                "\n" +
                "&lt;img src=\"/upload/images/0/5/83466ee8-76b0-4aea-a029-16723f50a1fe_.jpg\"/&gt;\n" +
                "</code></pre>\n" +
                "<p><img src=\" /upload/images/0/5/83466ee8-76b0-4aea-a029-16723f50a1fe_.jpg\"/></p>\n" +
                "\n");
        System.out.println(getArticleMetaFromContent(article).trim());


        System.out.println("------------");

        article.setMd("#Title\n>* Hello world\n>* hahahahahh");
        Markdown4jProcessor processor = new Markdown4jProcessor();
        String html = processor.process(article.getMd());
        System.out.println(html);
    }

    public static void main(String[] args) throws IOException {
        String html = " <h1>我的标题</h1>\n" +
                "<blockquote><ul>\n" +
                "<li>样例1\n" +
                "<br  />样例2</li>\n" +
                "<li>hahaha\n" +
                "<br  />啦啦啦</li>\n" +
                "</ul>\n" +
                "</blockquote>\n" +
                "<p><img src=\"/upload/images/0/5/83466ee8-76b0-4aea-a029-16723f50a1fe_.jpg\" alt=\"\" /></p>\n" +
                "<p>![](/upload/images/0/5/83466ee8-76b0-4aea-a029-16723f50a1fe_.jpg =100)</p>\n" +
                "<p><img src=\" /upload/images/0/5/83466ee8-76b0-4aea-a029-16723f50a1fe_.jpg\" width=\"100%\"/></p>\n" +
                "<pre><code>&lt;img src=\"/upload/images/0/5/83466ee8-76b0-4aea-a029-16723f50a1fe_.jpg\"/&gt;\n" +
                "\n" +
                "&lt;img src=\"/upload/images/0/5/83466ee8-76b0-4aea-a029-16723f50a1fe_.jpg\"/&gt;\n" +
                "</code></pre>\n" +
                "<p><img style=\"width:670px;height:70%;\" src=\" /upload/images/0/5/83466ee8-76b0-4aea-a029-16723f50a1fe_.jpg\"/></p>";
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("img");
        for(int i=0;i<elements.size();i++){
            Element element = elements.get(i);
            //img自有的width和height
            element.attr("width","100%");
            element.attr("height","");
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

        System.out.println(document.body().html());
    }

    @org.junit.Test
    public void testDateformat() throws IOException {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        System.out.print(format.format(date));
    }

    @org.junit.Test
    public void testMarkdown() throws IOException {
//        Markdown4jProcessor processor = new Markdown4jProcessor();
//        String content = processor.process(new File("D:\\1.txt"));
//        System.out.println(content);
//        System.out.println("success");
    }

    @org.junit.Test
    public void testDateFormat(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(date));
    }
}
