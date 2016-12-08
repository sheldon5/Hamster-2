package cn.coselding.hamster.web.manage;

import cn.coselding.hamster.dto.MarkDownImageResult;
import cn.coselding.hamster.utils.Config;
import cn.coselding.hamster.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片上传Controller
 * Created by 宇强 on 2016/10/4 0004.
 */
@Controller
@RequestMapping("/manage/upload")
public class UploadController implements ServletContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Config config;
    private String contextPath;

    @Override
    public void setServletContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
    }

    //CKEditor图片上传
    @RequestMapping(value = "/ckeditor", method = RequestMethod.POST)
    @ResponseBody
    public String ckeditor(@RequestParam("upload") MultipartFile upload,
                         @RequestParam(value = "CKEditorFuncNum") String callback) {
        //后缀名
        String filename = upload.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf('.') + 1);
        //返回体
        StringBuilder builder = new StringBuilder();
        //后缀名符合要求，上传
        if (extension.equals("jpg")||extension.equals("jpeg") || extension.equals("bmp") || extension.equals("gif") || extension.equals("png")) {
            //检查上传文件大小
            if (upload.getSize() > 600 * 1024) {
                builder.append("<script type=\"text/javascript\">");
                builder.append("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件大小不得大于600k');");
                builder.append("</script>");
                return builder.toString();
            }

            //合成服务器路径
            filename = WebUtils.encodeFilename(filename);
            //hash打散文件
            String savePath = WebUtils.encodePath(filename, config.getUploadImagePath());
            //文件持久化
            File file = new File(config.getUploadImagePath() + File.separator + savePath);
            try {
                upload.transferTo(file);
            } catch (IOException e) {
                logger.error(e.getMessage()+"CKEDITOR图片上传失败", e);
                builder.append("<script type=\"text/javascript\">");
                builder.append("showMsgDialog('CKEDITOR图片上传失败');");
                builder.append("</script>");
                return builder.toString();
            }

            //反馈客户端
            builder.append("<script type=\"text/javascript\">");
            builder.append("window.parent.CKEDITOR.tools.callFunction(" +
                    callback + ",'" +
                    contextPath + config.getUploadUrlImageCkeditor() +
                    "/" + filename + "','')");
            builder.append("</script>");
            logger.info("CKEDITOR图片上传成功");
        } else {//不符合要求，进行提示
            builder.append("<script type=\"text/javascript\">");
            builder.append("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件格式不正确（必须为.jpeg/.jpg/.gif/.bmp/.png文件）');");
            builder.append("</script>");
            logger.info("CKEDITOR图片上传：后缀名不合法");
        }
        //不反馈页面，这是后台AJax请求，返回脚本让浏览器执行即可
        return builder.toString();
    }

    //markdown图片上传
    @RequestMapping(value = "/markdown", method = RequestMethod.POST)
    @ResponseBody
    public MarkDownImageResult markdowmUpload(@RequestParam("editormd-image-file") MultipartFile upload) {
        //后缀名
        String filename = upload.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf('.') + 1);
        //后缀名符合要求，上传
        if (extension.equals("jpg")||extension.equals("jpeg") || extension.equals("bmp") || extension.equals("gif") || extension.equals("png")) {
            //检查上传文件大小
            if (upload.getSize() > 600 * 1024) {
                return new MarkDownImageResult(0,"文件大小不得大于600k","");
            }

            //合成服务器路径
            filename = WebUtils.encodeFilename(filename);
            //hash打散文件
            String savePath = WebUtils.encodePath(filename, config.getUploadImagePath());
            //文件持久化
            File file = new File(config.getUploadImagePath() + File.separator + savePath);
            try {
                upload.transferTo(file);
            } catch (IOException e) {
                logger.error(e.getMessage()+"Markdown图片上传失败", e);
                return new MarkDownImageResult(0,"Markdown图片上传失败","");
            }

            //反馈客户端
            logger.info("Markdown图片上传成功...：" + savePath);
            return new MarkDownImageResult(1,"Markdown图片上传成功",contextPath+config.getUploadUrlImageCkeditor()+"/"+filename);
        } else {//不符合要求，进行提示
            logger.info("Markdown图片上传：后缀名不合法");
            return new MarkDownImageResult(0,"文件格式不正确（必须为.jpeg/.jpg/.gif/.bmp/.png文件）","");
        }
    }

    //自定义图片上传
    @RequestMapping("/customer")
    @ResponseBody
    public Map<String,Object> customer(@RequestParam("upload") MultipartFile upload){
        //后缀名
        String filename = upload.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf('.') + 1);
        //返回体
        Map<String,Object> result = new HashMap<String,Object>();
        //后缀名符合要求，上传
        if (extension.equals("jpg")||extension.equals("jpeg") || extension.equals("bmp") || extension.equals("gif") || extension.equals("png")) {
            //检查上传文件大小
            if (upload.getSize() > 600 * 1024) {
                result.put("state",0);
                result.put("msg","文件大小不得大于600k");
                return result;
            }

            //合成服务器路径
            filename = WebUtils.encodeFilename(filename);
            //hash打散文件
            String savePath = WebUtils.encodePath(filename, config.getUploadImagePath());
            //文件持久化
            File file = new File(config.getUploadImagePath() + File.separator + savePath);
            try {
                upload.transferTo(file);
            } catch (IOException e) {
                result.put("state",0);
                result.put("msg","自定义图片上传失败");
                logger.error(e.getMessage()+"自定义图片上传失败", e);
                return result;
            }

            //反馈客户端
            result.put("state",1);
            result.put("msg",contextPath+config.getUploadUrlImageCkeditor()+"/"+filename);
            logger.info("自定义图片上传成功");
            return result;
        } else {//不符合要求，进行提示
            result.put("state",0);
            result.put("msg","文件格式不正确（必须为.jpeg/.jpg/.gif/.bmp/.png文件）");
            logger.info("自定义图片上传：后缀名不合法");
            return result;
        }
    }
}
