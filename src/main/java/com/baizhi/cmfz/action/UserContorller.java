package com.baizhi.cmfz.action;

import com.baizhi.cmfz.entity.Chapter;
import com.baizhi.cmfz.entity.User;
import com.baizhi.cmfz.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("user")
public class UserContorller {
    @Resource
    private UserService userService;

    @RequestMapping("queryByPage")
    @ResponseBody
    public HashMap<String,Object> queryByPage(Integer page,Integer rows){
        HashMap<String, Object> map = userService.queryByPage(page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public String edit(User user,String oper){
        String add=null;
        if("add".equals(oper)){
            add = userService.insert(user);
        }
        if("edit".equals(oper)){
            userService.update(user);
        }
        if("del".equals(oper)){
            userService.delete(user.getId());
        }
        return add;
    }

    @RequestMapping("upload")
    public void  upload(MultipartFile picImg, HttpServletRequest request, String id){
        userService.upload(picImg,request,id);
    }
}
