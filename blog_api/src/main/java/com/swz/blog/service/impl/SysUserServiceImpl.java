package com.swz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swz.blog.dao.SysUserDao;
import com.swz.blog.pojo.SysUser;
import com.swz.blog.service.SysUserService;
import com.swz.blog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 13:47
 * @Description: TODO:
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 查询作者
     *
     * @param authorId
     * @return
     */
    @Override
    public UserVo selectById (Long authorId){
        SysUser sysUser = sysUserDao.selectById(authorId);  //？？？null
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        return userVo;
    }

    @Override
    public SysUser selectByAccount (String account){
        QueryWrapper<SysUser> wrapper = new QueryWrapper();
        wrapper.eq("account", account).last("limit 0,1");
        SysUser sysUser = sysUserDao.selectOne(wrapper);
        return sysUser;
    }

    @Override
    public void register (SysUser sysUser){
        //系统默认为雪花算法添加
        sysUserDao.insert(sysUser);
    }
}
