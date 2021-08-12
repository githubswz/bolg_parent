package com.swz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swz.blog.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 13:04
 * @Description: TODO:
 */
@Mapper
@Repository
public interface SysUserDao extends BaseMapper<SysUser> {


}
