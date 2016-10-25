package cn.coselding.hamster.dao;

import cn.coselding.hamster.service.ThemeService;
import cn.coselding.hamster.service.impl.ThemeServiceImpl;
import org.junit.Test;

import java.io.File;

/**
 * Created by 宇强 on 2016/10/23 0023.
 */
public class ThemeServiceImplTest {

    ThemeService themeService = new ThemeServiceImpl();

    @Test
    public void testSetServletContext() throws Exception {

    }

    @Test
    public void testInitProp() throws Exception {

    }

    @Test
    public void testDefaultProp() throws Exception {

    }

    @Test
    public void testCopyFtl() throws Exception {

    }

    @Test
    public void testCopyJsp() throws Exception {

    }

    @Test
    public void testCopyCss() throws Exception {

    }

    @Test
    public void testCopyJs() throws Exception {

    }

    @Test
    public void testCopyIamges() throws Exception {

    }

    @Test
    public void testCopyFonts() throws Exception {

    }

    @Test
    public void testCopyPublic() throws Exception {

    }

    @Test
    public void testCopyDir() throws Exception {
//        themeServiceImpl.copyDir(new File("D:\\Java\\JavaWeb\\Tomcat\\webapps\\Hamster\\admin"),new File("D:\\admin"));
    }

    @Test
    public void testCopyFile() throws Exception {
//        File file = new File("D:\\admin");
//        deleteDir(file);
    }

    public void deleteDir(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f:files){
                deleteDir(f);
            }
        }
        file.delete();
    }
}