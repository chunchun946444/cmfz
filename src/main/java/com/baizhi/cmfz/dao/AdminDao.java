package com.baizhi.cmfz.dao;

import com.baizhi.cmfz.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {

    //登录
    public Admin login(@Param("name") String name,@Param("password") String password);

    //注册
    public void add(Admin admin);
}
