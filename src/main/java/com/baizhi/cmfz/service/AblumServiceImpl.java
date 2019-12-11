package com.baizhi.cmfz.service;

import com.baizhi.cmfz.dao.AlbumMapper;
import com.baizhi.cmfz.dao.ChapterMapper;
import com.baizhi.cmfz.entity.Album;
import com.baizhi.cmfz.entity.AlbumExample;
import com.baizhi.cmfz.entity.Chapter;
import com.baizhi.cmfz.entity.ChapterExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AblumServiceImpl implements AlbumService{
    @Resource
    private AlbumMapper albumMapper;
    @Resource
    private ChapterMapper chapterMapper;

    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //获得添加条件的对象
        AlbumExample albumExample = new AlbumExample();
        //调用底层方法进行查询总条数
        //获取总条数
        int i = albumMapper.selectCountByExample(albumExample);
        map.put("records",i);
        //获取总页数
        int i1 = i % rows == 0 ? i / rows : i / rows + 1;
        map.put("total",i1);
        //获取当前页数
        map.put("page",page);
        //获取当前页数据
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Album> albums = albumMapper.selectByRowBounds(new Album(), rowBounds);
        map.put("rows",albums);
        //返回
        return map;
    }


    //添加
    @Override
    public String add(Album album) {
        //创建一个新的id并指定给专辑对象
        String s = UUID.randomUUID().toString();
        album.setId(s);
        //指定上传时间
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        album.setPudDate(format);
        //指定初始的评分
        album.setScore(5.0);
        albumMapper.insert(album);
        return s;
    }

    //文件上传
    @Override
    public void  upload(MultipartFile coverImg, String id, HttpServletRequest request){
        //获取绝对路径
        String realPath = request.getServletContext().getRealPath("/album/img");
        //判断文件夹是否存在
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        //获取文件名
        String filename = coverImg.getOriginalFilename();
        //对文件名添加时间搓保证文件不会因为同名而上传失败
        String newName = new Date().getTime()+"-"+filename;
        try {
            //文件上传
            coverImg.transferTo(new File(realPath,newName));
            //上传完成后修改路径名
            Album album = new Album();
            album.setId(id);
            album.setCoverImg(newName);
            //根据主键修改
            AlbumExample example = new AlbumExample();
            example.createCriteria().andIdEqualTo(album.getId());
            albumMapper.updateByExampleSelective(album,example);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //修改
    @Override
    public void update(Album album) {
        //判断是否是上传文件,如果不是解决掉空串
        if(album.getCoverImg()==""){
            album.setCoverImg(null);
        }
        AlbumExample example = new AlbumExample();
        example.createCriteria().andIdEqualTo(album.getId());
        albumMapper.updateByExampleSelective(album,example);

    }

    //删除
    @Override
    public void delete(Album album) {
        ChapterExample example1 = new ChapterExample();
        example1.createCriteria().andAlbumIdEqualTo(album.getId());
        List<Chapter> chapters = chapterMapper.selectByExample(example1);
        if(chapters.size()==0) {
            AlbumExample example = new AlbumExample();
            example.createCriteria().andIdEqualTo(album.getId());
            albumMapper.deleteByExample(example);
        }
    }


}
