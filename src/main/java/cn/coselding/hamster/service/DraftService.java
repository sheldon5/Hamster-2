package cn.coselding.hamster.service;

import cn.coselding.hamster.domain.Article;
import cn.coselding.hamster.dto.Page;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 宇强 on 2016/10/11 0011.
 */
public interface DraftService {
    //增
    int addDraft(Article draft);

    //删
    void deleteDraft(int artid);

    //改
    @Transactional
    int updateDraft(Article draft);

    //查单
    Article queryDraft(int artid);

    //查页
    @Transactional
    Page<Article> queryPage(int pagenum, String url);

    //发布草稿
    void deploy(int artid);

    //设置为不发布
    void unDeploy(int artid);
}
