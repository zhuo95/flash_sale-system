package com.zz.miaosha.controller;

import com.zz.miaosha.domain.MiaoshaUser;
import com.zz.miaosha.redis.RedisService;
import com.zz.miaosha.service.MiaoshaUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Slf4j
@RequestMapping("/goods")
public class GoodController {
    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/to_list")
    public String toList(Model model, MiaoshaUser user)
//                         @CookieValue(value = MiaoshaUserService.TOKEN_NAME) String loginToken,
//                         @RequestParam(value = MiaoshaUserService.TOKEN_NAME) String paramToken)
                                                                                                { //有时候会放到param 参数里
        model.addAttribute("user",user);
        return "goods_list";
    }

}
