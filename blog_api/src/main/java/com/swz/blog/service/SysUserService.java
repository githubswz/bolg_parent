package com.swz.blog.service;

import com.swz.blog.pojo.SysUser;
import com.swz.blog.vo.UserVo;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 13:46
 * @Description: TODO:
 */
public interface SysUserService {
    UserVo selectById (Long authorId);

    SysUser selectByAccount (String account);

    void register (SysUser sysUser);
}
