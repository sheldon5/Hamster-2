package cn.coselding.hamster.dto;

import java.io.File;

/**
 * Created by 宇强 on 2016/10/23 0023.
 */
public class Theme {

    private String name;
    private String path;
    private String logo;

    @Override
    public String toString() {
        return "Theme{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
