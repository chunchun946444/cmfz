package com.baizhi.cmfz.action;

import com.baizhi.cmfz.entity.Article;
import com.baizhi.cmfz.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@RequestMapping("article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    //分页查询
    @RequestMapping("queryByPage")
    @ResponseBody
    public HashMap<String,Object> queryByPage(Integer page,Integer rows){
        HashMap<String, Object> map = articleService.queryByPage(page, rows);
        return map;
    }

    //增删改
    @RequestMapping("edit")
    @ResponseBody
    public String edit(Article article,String oper){
        String add =null;
        System.out.println(oper);
        if(oper.equals("add")){
            articleService.add(article);
        }
        if(oper.equals("edit")){
            articleService.update(article);
        }
        if(oper.equals("del")){
            articleService.delete(article.getId());
        }
        return add;
    }

}
