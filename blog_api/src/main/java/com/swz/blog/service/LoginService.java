package com.swz.blog.service;

import com.swz.blog.vo.Result;
import com.swz.blog.vo.param.LoginParam;

/**
 * @author : 苏文致
 * @date Date : 2021年07月22日 11:14
 * @Description: TODO:
 */
public interface LoginService {
    String login (LoginParam loginParam);

    Result currentUser (String token);

    Result logOut (String tooken);

    Result register (LoginParam register);
}
