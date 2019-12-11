package com.baizhi.cmfz.service;

import com.baizhi.cmfz.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public interface ChapterService {
    //查询
    public HashMap<String,Object> queryByPage(Integer page, Integer rows, String albumId);

    //添加
    public String add(Chapter chapter);

    //修改方法
    void update(Chapter chapter);

    //删除
    void delete(String id);

    //文件上传
    void upload(MultipartFile src, HttpServletRequest request,String id);

    //文件下载
    public void download(HttpServletRequest request, String filename, HttpServletResponse response);

    /*//文件在线打开
    public void inline(HttpServletRequest request, String filename, HttpServletResponse response);
*/
}
