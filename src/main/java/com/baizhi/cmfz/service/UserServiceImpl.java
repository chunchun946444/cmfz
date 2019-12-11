package com.baizhi.cmfz.service;

import com.baizhi.cmfz.dao.UserMapper;
import com.baizhi.cmfz.entity.Album;
import com.baizhi.cmfz.entity.AlbumExample;
import com.baizhi.cmfz.entity.User;
import com.baizhi.cmfz.entity.UserExample;
import com.baizhi.cmfz.util.Md5Utils;
import com.baizhi.cmfz.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public String insert(User user) {
        String id=null;
        //设置一些初始值
        //id
        id = UUIDUtil.getUUID();
        user.setId(id);
        //salt
        String salt = Md5Utils.getSalt(6);
        user.setSalt(salt);
        //password
        String md5Code = Md5Utils.getMd5Code(user.getPassword() + salt);
        user.setPassword(md5Code);
        //status
        user.setStatus("正常");
        //city
        user.setCity("全国");
        //regTime
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        user.setRegTime(format);
        userMapper.insertSelective(user);
        return id;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        UserExample userExample = new UserExample();
        //获得总条数
        int i = userMapper.selectCountByExample(userExample);
        map.put("records",i);
        //获得总页数
        int i1 = i % rows == 0 ? i / rows : i / rows + 1;
        map.put("total",i1);
        //获得当前
        map.put("page",page);
        //获得当前页数据
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<User> users = userMapper.selectByExampleAndRowBounds(userExample, rowBounds);
        map.put("rows",users);
        return map;
    }

    @Override
    public void update(User user) {
        if(user.getPicImg()==""){
            user.setPicImg(null);
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(user.getId());
        userMapper.updateByExampleSelective(user,userExample);
    }

    @Override
    public void delete(String id) {
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);
        userMapper.deleteByExample(example);
    }

    @Override
    public void upload(MultipartFile picImg, HttpServletRequest request, String id) {
        //获取绝对路径
        String realPath = request.getServletContext().getRealPath("/user/img");
        //判断文件夹是否存在
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        //获取文件名
        String filename = picImg.getOriginalFilename();
        //对文件名添加时间搓保证文件不会因为同名而上传失败
        String newName = new Date().getTime()+"-"+filename;
        try {
            //文件上传
            picImg.transferTo(new File(realPath,newName));
            //上传完成后修改路径名
            User user = new User();
            user.setId(id);
            user.setPicImg(newName);
            //根据主键修改
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(user.getId());
            userMapper.updateByExampleSelective(user,userExample);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
