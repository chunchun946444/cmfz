package com.baizhi.cmfz.dao;

import com.baizhi.cmfz.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    //分页查询
    List<Banner> selectAll(@Param("page") Integer page, @Param("rows") Integer rows);
    //添加
    void add(Banner banner);
    //删除
    void delete(String id);
    //修改
    void update(Banner banner);
    //查总条数
    int queryCount();
    //根据一个id查询一个图
    Banner queryByid(String id);

}
