package com.baizhi.cmfz.service;

import com.baizhi.cmfz.entity.Article;
import com.baizhi.cmfz.entity.Chapter;

import java.util.HashMap;

public interface ArticleService {
    //分页查询
    public HashMap<String,Object> queryByPage(Integer page,Integer rows);
    //添加
    public void add(Article article);

    //修改方法
    void update(Article article);

    //删除
    void delete(String id);
}
