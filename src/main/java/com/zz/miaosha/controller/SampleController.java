package com.zz.miaosha.controller;

import com.zz.miaosha.domain.User;
import com.zz.miaosha.redis.RedisService;
import com.zz.miaosha.redis.UserKey;
import com.zz.miaosha.result.Result;
import com.zz.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbget(){
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/ts")
    @ResponseBody
    public Result<Boolean> dbts(){
        boolean success = userService.ts();
        return Result.success(success);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<String> redisget(){
        redisService.set(UserKey.getById,"1","123456");
        String res = redisService.get(UserKey.getById,"1",String.class);
        return Result.success(res);
    }
}
