package com.baizhi.cmfz;

import com.baizhi.cmfz.dao.AdminDao;
import com.baizhi.cmfz.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzZcApplication.class)
public class CmfzZcApplicationTests {
    /*@Autowired
    private AdminMapper adminMapper;

    @Test
    public void test2(){
        adminMapper.insertSelective(new Admin("ssss","da够","5555"));
    }

    @Test
    public void test3(){
        adminMapper.delete("sss");
    }*/
    @Resource
    private AdminDao adminDao;
    @Test
    public void test2(){
        adminDao.add(new Admin(UUID.randomUUID().toString(),"大黄","456"));
    }



}
