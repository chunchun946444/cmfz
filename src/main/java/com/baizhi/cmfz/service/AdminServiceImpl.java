package com.baizhi.cmfz.service;

import com.baizhi.cmfz.dao.AdminDao;
import com.baizhi.cmfz.entity.Admin;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@TituloEleitoral
public class AdminServiceImpl implements AdminService {
    @Autowired      //自动注入dao对象
    private AdminDao adminDao;

    @Override       //注册
    public void insert(Admin admin) {
            adminDao.add(admin);
    }

    @Override       //登录
    public Admin login(String name, String password) {
        Admin login = adminDao.login(name, password);
        return login;
    }
}
