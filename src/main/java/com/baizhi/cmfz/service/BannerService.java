package com.baizhi.cmfz.service;

import com.baizhi.cmfz.entity.Banner;

import java.util.HashMap;
import java.util.List;

public interface BannerService {
    //分页查询
    public HashMap<String,Object> queryByPage(Integer page,Integer rows);
    //添加
    public String add(Banner banner);
    //修改
    public void update(Banner banner);

    public void update2(Banner banner);
    //删除
    public void delete(String id);
    //根据一个id查一个
    public Banner queryByid(String id);
}
