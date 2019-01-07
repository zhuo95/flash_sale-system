package com.zz.miaosha.service;

import com.zz.miaosha.dao.MiaoshaUserDao;
import com.zz.miaosha.domain.MiaoshaUser;
import com.zz.miaosha.exception.GlobalException;
import com.zz.miaosha.redis.MiaoshaUserKey;
import com.zz.miaosha.redis.RedisService;
import com.zz.miaosha.result.CodeMsg;
import com.zz.miaosha.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zz.miaosha.util.MD5Util;
import com.zz.miaosha.vo.LoginVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class MiaoshaUserService {
    @Autowired
    private MiaoshaUserDao miaoshaUserDao;
    @Autowired
    private RedisService redisService;

    public final static String TOKEN_NAME = "zz_token";

    public MiaoshaUser getById(Long id){
        return miaoshaUserDao.getById(id);
    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo==null) throw new GlobalException(CodeMsg.SERVER_ERROR);
        String mobile = loginVo.getMobile();
        String pass = loginVo.getPassword();
        //判断是否存在
        MiaoshaUser user = miaoshaUserDao.getById(Long.parseLong(mobile));
        if(user==null) throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        //验证密码
        String dbpass = user.getPassword();
        String salt = user.getSalt();
        String calPass = MD5Util.formPassToDBPass(pass,salt);
        if (!StringUtils.equals(dbpass,calPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        addCookie(response,user);
        return true;
    }

    private void addCookie(HttpServletResponse response, MiaoshaUser user){
        //登录成功 生成session
        String token = UUIDUtil.uuid();
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(TOKEN_NAME,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expiireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public MiaoshaUser getByToken(String token,HttpServletResponse response) {
        if (StringUtils.isEmpty(token)) return null;
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效时间
        if (user!=null) addCookie(response,user);
        return user;
    }

    public boolean signup(MiaoshaUser user){
        if (user == null) throw new GlobalException(CodeMsg.SERVER_ERROR);
        String salt = "zz_2_salt";
        user.setSalt(salt);
        String pass = MD5Util.formPassToDBPass(user.getPassword(),user.getSalt());
        user.setPassword(pass);

        int res = miaoshaUserDao.insertUser(user);
        return res==0;
    }
}

