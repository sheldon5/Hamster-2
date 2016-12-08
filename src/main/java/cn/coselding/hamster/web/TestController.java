package cn.coselding.hamster.web;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by coselding on 16/12/4.
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public Map test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        File file = new File("/Users/coselding/个人网站/Hamster-2/Hamster-2/src/main/webapp/Pushy.md");
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        IOUtils.read(fis, buffer);
        String str = new String(buffer);
        System.out.println(str);
        Map map = new HashMap();
        map.put("md",str);
        return map;
    }
}
