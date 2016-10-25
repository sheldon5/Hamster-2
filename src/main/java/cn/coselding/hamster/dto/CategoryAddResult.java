package cn.coselding.hamster.dto;

import cn.coselding.hamster.domain.Category;

import java.util.List;

/**ajax添加类别返回值
 * Created by 宇强 on 2016/10/8 0008.
 */
public class CategoryAddResult {

    private int state;//0表示失败，1表示成功
    private List<Category> categories;

    public CategoryAddResult(int state, List<Category> categories) {
        this.state = state;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "CategoryAddResult{" +
                "state=" + state +
                ", categories=" + categories +
                '}';
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
