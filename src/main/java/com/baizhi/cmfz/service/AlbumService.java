package com.baizhi.cmfz.service;

import com.baizhi.cmfz.entity.Album;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface AlbumService {

    //分页查询
    public HashMap<String,Object> queryByPage(Integer page,Integer rows);

    //添加
    public String add(Album album);

    //文件上传
    public void  upload(MultipartFile coverImg, String id, HttpServletRequest request);

    //文件修改
    public void update(Album album);

    //删除
    public void delete(Album album);
}
