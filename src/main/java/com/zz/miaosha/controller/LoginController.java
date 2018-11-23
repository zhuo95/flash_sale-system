package com.zz.miaosha.controller;

import com.zz.miaosha.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vo.LoginVo;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @RequestMapping
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> do_login(LoginVo loginVo){
        // log.info(loginVo.toString());\
        //校验蚕食
        return null;
    }
}
