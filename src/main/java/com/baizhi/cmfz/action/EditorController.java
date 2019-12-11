package com.baizhi.cmfz.action;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("editor")
public class EditorController {

    @RequestMapping("uploadPhoto")
    @ResponseBody
    public HashMap<String, Object> uploadPhoto(MultipartFile imgFile, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        String realPath = request.getServletContext().getRealPath("/user/img");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String filename = imgFile.getOriginalFilename();
        String newName = new Date().getTime()+"-"+filename;
        try {
            imgFile.transferTo(new File(realPath,newName));
            //获得http
            String scheme = request.getScheme();
            //获得应用名
            String serverName = request.getServerName();
            //获得端口
            int serverPort = request.getServerPort();
            //获得
            String contextPath = request.getContextPath();
            String url = scheme+"://"+serverName+":"+serverPort+"/"+contextPath+"/user/img/"+newName;
            map.put("error",0);
            map.put("url",url);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error",1);
            map.put("message","上传异常");
        }
        return map;
    }

    @RequestMapping("download")
    @ResponseBody
    public HashMap<String, Object> download(HttpServletRequest request){
        HashMap<String, Object> maps = new HashMap<>();

        ArrayList<Object> lists = new ArrayList<>();

        HashMap<String, Object> map = new HashMap<>();

        String realPath = request.getServletContext().getRealPath("/user/img");
        //获取文件
        File file = new File(realPath);
        //获取文件下的所有文件名
        String[] names = file.list();
        //遍历所有文件
        for (int i = 0; i < names.length; i++) {
            //获取单个文件名
            String name = names[i];
            map.put("is_dir",false);        //是否是文件夹
            map.put("has_file",false);      //是否有文件
            File file1 = new File(realPath, name);
            map.put("filesize",file1.length());  //将文件放入路径获取大小
            //map.put("dir_path","");
            map.put("is_photo",true);       //根据上一步骤判断是否是图片
            String extension = FilenameUtils.getExtension(name);   //获得文件扩展名
            map.put("filetype",extension);         //图片类型
            map.put(i+"filename",name);         //图片名
            String[] split = name.split("-");
            //long l =Long.parseLong(split[0]);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                Date parse = dateFormat.parse(split[0]);
                map.put("datetime",parse);      //时间挫转化为指定格式的日期
            } catch (ParseException e){
                e.printStackTrace();
            }
            lists.add(map);     //将封装好的内层图片信息放入集合中
            maps.put("current_url","http://localhost:44444/cmfz/user/img/");
            maps.put("total_count",lists.size());
            maps.put("file_list",lists);
        }
        /*maps.put("current_url","http://localhost:44444/cmfz/user/img/");
        maps.put("total_count",lists.size());
        maps.put("file_list",lists);*/
        return maps;
    }
}
