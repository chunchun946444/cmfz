package com.baizhi.cmfz;

import com.baizhi.cmfz.dao.BannerDao;
import com.baizhi.cmfz.entity.Banner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzZcApplication.class)
public class BannerTest {
    @Autowired
    private BannerDao bannerDao;

    @Test
    public void te(){
        Banner banner = new Banner();
        banner.setId("ae6a5938-8053-4e11-9f91-004f7c18ea16");
        banner.setStatus("0");
        System.out.println("你好啊");
        bannerDao.update(banner);
        System.out.println("你好吗？");
        System.out.println("java好难啊!");
    }
}
