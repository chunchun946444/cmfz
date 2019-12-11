package com.baizhi.cmfz.service;

import com.baizhi.cmfz.dao.ChapterMapper;
import com.baizhi.cmfz.entity.Chapter;
import com.baizhi.cmfz.entity.ChapterExample;
import com.baizhi.cmfz.util.UUIDUtil;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.RowBounds;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    //分页查询
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows, String albumId) {
        ChapterExample example = new ChapterExample();
        HashMap<String, Object> map = new HashMap<>();
        //获得总条数
        int i = chapterMapper.selectCountByExample(example);
        map.put("records",i);
        //获得总页数
        int i1 = i % rows == 0 ? i / rows : i / rows + 1;
        map.put("total",i1);
        //获得当前页
        map.put("page",page);
        //获得当前页数据
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        example.createCriteria().andAlbumIdEqualTo(albumId);
        List<Chapter> chapters = chapterMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",chapters);
        return map;
    }

    //添加
    @Override
    public String add(Chapter chapter) {
        //初始化章节的一些参数
        String uuid = UUIDUtil.getUUID();
        chapter.setId(uuid);
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        chapter.setUploadTime(format);
        //添加
        chapterMapper.insertSelective(chapter);
        return uuid;
    }

    //修改
    @Override
    public void update(Chapter chapter) {
        if(chapter.getSrc()==""){
            chapter.setSrc(null);
        }
        ChapterExample example = new ChapterExample();
        example.createCriteria().andIdEqualTo(chapter.getId());
        chapterMapper.updateByExampleSelective(chapter,example);
    }

    @Override
    public void delete(String id) {
        ChapterExample example = new ChapterExample();
        example.createCriteria().andIdEqualTo(id);
        chapterMapper.deleteByExample(example);
    }

    @Override
    public void upload(MultipartFile src, HttpServletRequest request,String id){
        //根据相对路径获取存储绝对路径
        String realPath = request.getServletContext().getRealPath("/album/chapter");
        //判断文件夹是否存在
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        //获取文件名
        String filename = src.getOriginalFilename();
        //对文件名添加时间戳
        String newName = new Date().getTime() + "-" + filename;
        //文件上传
        File file1 = new File(realPath, newName);
        try {
            src.transferTo(file1);
            //获取文件的大小
            long size = src.getSize();  //直接获取文件的字节数
            String s = String.valueOf(size);    //将长整形的字节数转为字符串
            Double aDouble = Double.valueOf(s)/1024/1024;     //再将字符串转为双精度数据类型
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String sizes = decimalFormat.format(aDouble)+"MB";
            //获取文件的时长
            AudioFile read = AudioFileIO.read(new File(realPath, newName));
            AudioHeader audioHeader = read.getAudioHeader();
            //获取秒
            int sss = audioHeader.getTrackLength();
            String sc = sss/60+"分"+sss%60+"秒";

            Chapter chapter = new Chapter();
            //上传完毕修改资料
            chapter.setSize(sizes);
            chapter.setId(id);
            chapter.setSrc(newName);
            chapter.setDuration(sc);
            //根据主键修改
            ChapterExample example = new ChapterExample();
            example.createCriteria().andIdEqualTo(id);
            chapterMapper.updateByExampleSelective(chapter,example);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void download(HttpServletRequest request, String filename, HttpServletResponse response) {
        //根据相对路径获取绝对路径
        String realPath = request.getServletContext().getRealPath("/album/chapter");
        //创建读入流
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(realPath, filename));
            //设置响应头
            response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(filename,"UTF-8"));
            //文件下载
            IOUtils.copy(fileInputStream,response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
