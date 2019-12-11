package com.baizhi.cmfz.action;

import com.baizhi.cmfz.entity.Album;
import com.baizhi.cmfz.service.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("album")
public class AlbumController {
    @Resource
    private AlbumService albumService;

    @RequestMapping("queryByPage")
    @ResponseBody
    public HashMap<String,Object> queryByPage(Integer page,Integer rows)throws Exception{
        HashMap<String, Object> map = albumService.queryByPage(page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public String edit(Album album,String oper)throws Exception{
        String add=null;
        if("add".equals(oper)){
            add = albumService.add(album);
        }
        if ("edit".equals(oper)){

            albumService.update(album);

        }
        if("del".equals(oper)){
            albumService.delete(album);
        }
        return add;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile coverImg, String id, HttpServletRequest request)throws Exception{
        albumService.upload(coverImg,id,request);
    }
}
