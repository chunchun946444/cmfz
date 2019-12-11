package com.baizhi.cmfz.service;

import com.baizhi.cmfz.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface AdminService {
    //注册
    public void insert(Admin admin);

    //登录
    public Admin login(String name,String password);
}
