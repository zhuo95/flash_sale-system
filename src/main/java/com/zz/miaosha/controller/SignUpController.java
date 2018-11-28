package com.zz.miaosha.controller;

import com.zz.miaosha.domain.MiaoshaUser;
import com.zz.miaosha.result.CodeMsg;
import com.zz.miaosha.result.Result;
import com.zz.miaosha.service.MiaoshaUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/sign_up")
@Slf4j
public class SignUpController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @RequestMapping
    public String toLogin(){
        return "signup";
    }

    @RequestMapping("/do_signup")
    @ResponseBody
    public Result<Boolean> signUp(MiaoshaUser user){
        System.out.println(user.toString());
        user.setRegisterDate(new Date());
        boolean res = miaoshaUserService.signup(user);
        if(res) return Result.success(true);
        return Result.error(CodeMsg.SERVER_ERROR);
    }
}
