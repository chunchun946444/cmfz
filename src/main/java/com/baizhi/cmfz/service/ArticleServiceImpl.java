package com.baizhi.cmfz.service;

import com.baizhi.cmfz.dao.ArticleMapper;
import com.baizhi.cmfz.entity.Article;
import com.baizhi.cmfz.entity.ArticleExample;
import com.baizhi.cmfz.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.action.PutAllAction;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //获得总条数
        ArticleExample example = new ArticleExample();
        int i = articleMapper.selectCountByExample(example);
        map.put("records",i);
        //获得总页数
        int i1 = i % rows == 0 ? i / rows : i / rows + 1;
        map.put("total",i1);
        //获得当前页
        map.put("page",page);
        //获得当前页数据
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Article> articles = articleMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",articles);
        return map;
    }

    @Override
    public void add(Article article) {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        article.setUploadTime(format);
        article.setId(UUIDUtil.getUUID());
        articleMapper.insertSelective(article);
    }

    @Override
    public void update(Article article) {
        ArticleExample example = new ArticleExample();
        example.createCriteria().andIdEqualTo(article.getId());
        articleMapper.updateByExampleSelective(article,example);
    }

    @Override
    public void delete(String id) {
        System.out.println("======");
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andIdEqualTo(id);
        articleMapper.deleteByExample(articleExample);
    }
}
