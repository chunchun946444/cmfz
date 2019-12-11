package com.baizhi.cmfz.action;

import com.baizhi.cmfz.entity.Chapter;
import com.baizhi.cmfz.service.ChapterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequestMapping("chapter")
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    //分页查询
    @RequestMapping("queryByPage")
    @ResponseBody
    public HashMap<String,Object> queryByPage(Integer page,Integer rows,String albumId)throws Exception{
        HashMap<String, Object> map = chapterService.queryByPage(page, rows, albumId);
        return map;
    }

    //增删改操作
    @RequestMapping("edit")
    @ResponseBody
    public String edit(Chapter chapter,String albumId,String oper){
        String add=null;
        if("add".equals(oper)){
            chapter.setAlbumId(albumId);
            add = chapterService.add(chapter);
        }
        if("edit".equals(oper)){
            chapterService.update(chapter);
        }
        if("del".equals(oper)){
            chapterService.delete(chapter.getId());
        }
        return add;
    }

    //文件上传
    @RequestMapping("upload")
    public void  upload(MultipartFile src, HttpServletRequest request,String id){
        System.out.println(src+"controller");
        chapterService.upload(src,request,id);
    }


    //文件下载
    @RequestMapping("download")
    public void download(HttpServletRequest request, String filename, HttpServletResponse response){
        chapterService.download(request,filename,response);
    }

    /*//在线播放
    @RequestMapping("inline")
    public void inline(HttpServletRequest request, String filename, HttpServletResponse response){
        chapterService.inline(request,filename,response);
    }*/

}
