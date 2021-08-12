package com.swz.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.swz.blog.pojo.SysUser;
import com.swz.blog.service.LoginService;
import com.swz.blog.service.SysUserService;
import com.swz.blog.utils.JWTUtils;
import com.swz.blog.vo.ErrorCode;
import com.swz.blog.vo.LoginUserVo;
import com.swz.blog.vo.Result;
import com.swz.blog.vo.param.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : 苏文致
 * @date Date : 2021年07月22日 11:24
 * @Description: TODO:
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    private static final String SALT = "mszlu!@#";

    @Override
    public String login (LoginParam loginParam){
        /**
         * 1.密码MD5+salt加密：
         * 2.查询数据库获得用户信息校验
         * 3.生成tooken
         * 4.将对象信息存放到redis，用来以后的的token校验
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        //空校验
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            return null;
        }
        //密码校验
        String md5Pwd = DigestUtils.md5Hex(password + SALT);
        SysUser sysUser = sysUserService.selectByAccount(account);
        if (ObjectUtils.isEmpty(sysUser))
            return null;
        if (!md5Pwd.equals(sysUser.getPassword())) {
            return null;
        }
        //生成token
        String token = JWTUtils.createToken(sysUser.getId());//用用户的id作为token的识别
        //将token对应的用户信息保存到redis，时间设置为1天
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return token;
    }

    @Override
    public Result currentUser (String token){
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null)
            //用户没有登录
            return Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
        //拿到用户信息
        String s = redisTemplate.opsForValue().get("TOKEN_" + token) + "";
        SysUser sysUser = JSON.parseObject(s, SysUser.class);
        LoginUserVo loginUserVo = new LoginUserVo();
        BeanUtils.copyProperties(sysUser, loginUserVo);
        return Result.success(loginUserVo);
    }

    /**
     * 退出操作：删除redis中的token信息
     *
     * @param token
     * @return
     */
    @Override
    public Result logOut (String token){

        if (StringUtils.isEmpty(token))
            return Result.fail(-5555, "tooken有误");
        //删除redis中的token
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    /**
     * 1.判读用户为空
     * 2.判断用户存在
     */
    @Override
    public Result register (LoginParam loginParam){
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = sysUserService.selectByAccount(account);
        if (ObjectUtils.isNotEmpty(sysUser)) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }

        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + SALT));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.register(sysUser);

        //生成token
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
