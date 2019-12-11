package com.baizhi.cmfz.service;

import com.baizhi.cmfz.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface UserService {
    //添加新用户
    public String insert(User user);
    //分页查询
    public HashMap<String,Object> queryByPage(Integer page,Integer rows);
    //修改用户
    public void update(User user);
    //删除
    public void delete(String id);
    //文件上传
    public void  upload(MultipartFile picImg, HttpServletRequest request, String id);
}
