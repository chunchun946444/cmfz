package com.baizhi.cmfz.dao;

import com.baizhi.cmfz.entity.User;
import com.baizhi.cmfz.entity.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
}