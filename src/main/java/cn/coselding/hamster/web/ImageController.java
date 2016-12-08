package cn.coselding.hamster.web;

import cn.coselding.hamster.utils.Config;
import cn.coselding.hamster.utils.WebUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by coselding on 16/12/1.
 */
@Controller
public class ImageController {

    @Autowired
    private Config config;

    @RequestMapping("/upload/images/{imageFilename}.{ext}")
    public ModelAndView getImage(@PathVariable("imageFilename") String imageFilename,
                                 @PathVariable("ext") String ext,
                                 HttpServletResponse response) {
        String savePath = config.getUploadImagePath();
        File image = new File(savePath + "/" + imageFilename + "." + ext);
        if (image.exists()) {//兼容原来的存储方式
            copyImage2Response(response, image);
            return null;
        } else {//原来的没有，找现在的打散文件夹中
            String path = savePath + "/" + WebUtils.encodePath(imageFilename + "." + ext, savePath);
            if (new File(path).exists()) {
                copyImage2Response(response, new File(path));
                return null;
            }
        }
        return null;
    }

    @RequestMapping("/upload/images/{dir1}/{dir2}/{imageFilename}.{ext}")
    public ModelAndView getImage1(@PathVariable("imageFilename") String imageFilename,
                                  @PathVariable("ext") String ext,
                                  @PathVariable("dir1") String dir1,
                                  @PathVariable("dir2") String dir2,
                                  HttpServletResponse response) {
        String savePath = config.getUploadImagePath();
        File image = new File(savePath + "/" + dir1 + "/" + dir2 + "/" + imageFilename + "." + ext);
        if (image.exists()) {
            copyImage2Response(response, image);
            return null;
        }
        return null;
    }

    //复制图片到响应体
    private void copyImage2Response(HttpServletResponse response, File image) {
        response.setContentType("image/*");
        response.setContentLength((int) image.length());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(image);
            IOUtils.copy(fis, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
                response.getOutputStream().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
