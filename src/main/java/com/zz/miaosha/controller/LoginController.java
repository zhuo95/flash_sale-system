package com.zz.miaosha.controller;

import com.zz.miaosha.result.CodeMsg;
import com.zz.miaosha.result.Result;
import com.zz.miaosha.service.MiaoshaUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zz.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @RequestMapping
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> do_login(@Valid LoginVo loginVo, HttpServletResponse response){
        // log.info(loginVo.toString());\
//        //校验参数
//        String passInput = loginVo.getPassword();
//        String mobile = loginVo.getMobile();
//        if(StringUtils.isEmpty(passInput)){
//            return Result.error(CodeMsg.PASSWORD_EMPTY);
//        }
//        if(StringUtils.isEmpty(mobile)){
//            return Result.error(CodeMsg.MOBILE_EMPTY);
//        }
//        if (!ValidatorUtil.isMobile(mobile)){
//            return Result.error(CodeMsg.MOBILE_ERROR);
//        }
        //登录
        miaoshaUserService.login(response,loginVo);
        return Result.success(true);
    }
}
