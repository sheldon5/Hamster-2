package cn.coselding.hamster.service.impl;

import cn.coselding.hamster.dao.ArticleDao;
import cn.coselding.hamster.dao.DraftDao;
import cn.coselding.hamster.domain.Article;
import cn.coselding.hamster.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by 宇强 on 2016/10/11 0011.
 */
@Service
public class DraftServiceImpl implements cn.coselding.hamster.service.DraftService {

    @Autowired
    private DraftDao draftDao;
    @Autowired
    private ArticleDao articleDao;

    //增
    @Override
    public int addDraft(Article draft){
        draft.setDeploy(1);
        return articleDao.saveArticle(draft);
    }

    //删
    @Override
    public void deleteDraft(int artid){
        articleDao.deleteArticle(artid);
    }

    //改
    @Override
    @Transactional
    public int updateDraft(Article draft){
        Article article = articleDao.queryArticle(draft.getArtid());
        article.setType(draft.getType());
        article.setCid(draft.getCid());
        article.setTitle(draft.getTitle());
        article.setTime(draft.getTime());
        article.setMeta(draft.getMeta());
        article.setTop(draft.getTop());
        article.setEditor(draft.getEditor());
        article.setMd(draft.getMd());
        article.setContent(draft.getContent());
        article.setDeploy(draft.getDeploy());
        return articleDao.updateArticle(article);
    }

    //查单
    @Override
    public Article queryDraft(int artid){
        return articleDao.queryArticle(artid);
    }

    //查页
    @Override
    @Transactional
    public Page<Article> queryPage(int pagenum, String url){
        int count = draftDao.queryCount();
        Page<Article> page = new Page<Article>(count,pagenum);
        List<Article> list = draftDao.getPageData(page.getStartindex(),page.getPagesize());
        page.setList(list);
        page.setUrl(url);
        return page;
    }

    //发布草稿
    @Override
    public void deploy(int artid){
        draftDao.setDeploy(artid,0);
    }

    //设置为不发布
    @Override
    public void unDeploy(int artid){
        draftDao.setDeploy(artid,1);
    }
}
