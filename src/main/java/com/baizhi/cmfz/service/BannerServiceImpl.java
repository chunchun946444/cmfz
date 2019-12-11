package com.baizhi.cmfz.service;

import com.baizhi.cmfz.dao.BannerDao;
import com.baizhi.cmfz.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    public HashMap<String,Object> queryByPage(Integer page,Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //获得总条数
        Integer i = bannerDao.queryCount();
        map.put("records",i);
        //获得总页数total
        Integer total = i % rows == 0 ? i / rows : i / rows + 1;
        map.put("total",total);
        //获得数据rows
        List<Banner> banners = bannerDao.selectAll(page, rows);
        map.put("rows",banners);
        //当前页page
        map.put("page",page);
        return map;
    }

    @Override
    public String add(Banner banner) {
        //生成随机id
        String s = UUID.randomUUID().toString();
        banner.setId(s);
        //设置默认状态
        banner.setStatus("1");
        //设置上传时间
        String dateStr=new SimpleDateFormat("YYYY-MM-dd").format(new Date());
        banner.setUploaddate(dateStr);

        bannerDao.add(banner);
        return s;
    }

    @Override
    public void update(Banner banner) {
        bannerDao.update(banner);
    }

    public void update2(Banner banner){
        System.out.println("====="+banner+"======");
        banner.setSrc(null);
        bannerDao.update(banner);
    }


    @Override
    public void delete(String id) {
        bannerDao.delete(id);
    }

    @Override
    public Banner queryByid(String id) {
        Banner banner = bannerDao.queryByid(id);
        if(banner.getStatus().equals("1")){
            banner.setStatus("0");
        }else banner.setStatus("1");
        return banner;
    }
}
