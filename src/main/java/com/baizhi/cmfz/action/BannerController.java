package com.baizhi.cmfz.action;

import com.baizhi.cmfz.entity.Banner;
import com.baizhi.cmfz.service.BannerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("banner")
public class BannerController {
    @Resource
    private BannerService bannerService;

    //分页查询
    @RequestMapping("queryAll")
    @ResponseBody
    public HashMap<String,Object> queryAll(Integer page,Integer rows)throws Exception{
        HashMap<String, Object> map = bannerService.queryByPage(page, rows);
        return map;
    }

    //添加数据
    @RequestMapping("edit")
    @ResponseBody
    public String edit(Banner banner,String oper)throws Exception{
        String add=null;
        //判断页面提交的操作
        if(oper.equals("add")){
            add = bannerService.add(banner);
        }
        if(oper.equals("edit")){
            bannerService.update2(banner);
        }
        if(oper.equals("del")){
            bannerService.delete(banner.getId());
        }
        return add;
    }

    @RequestMapping("upload")
    @ResponseBody
    public void  upload(MultipartFile src, String id, HttpServletRequest request)throws Exception{
        //根据相对路径获得绝对路径
        String realPath = request.getServletContext().getRealPath("banner/img");
        //判断文件路径是否存在
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        //获取文件名
        String fileName = src.getOriginalFilename();
        String newName =new Date().getTime()+"-"+fileName;   //保证相同图片在不同名字下能创建成功
        //文件上传
        File file1 = new File(realPath, newName);
        src.transferTo(file1);

        //上传完毕就调用方法修改数据库的路径
        Banner banner = new Banner();
        banner.setId(id);
        banner.setSrc(newName);
        bannerService.update(banner);
    }

    //修改状态
    @RequestMapping("updateStatus")
    public void  updateStatus(String id)throws Exception{
        Banner banner = bannerService.queryByid(id);
        bannerService.update(banner);
    }

}
