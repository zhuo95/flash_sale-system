package com.zz.miaosha.Config;

import com.zz.miaosha.domain.MiaoshaUser;
import com.zz.miaosha.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private MiaoshaUserService miaoshaUserService;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == MiaoshaUser.class; //只有miaoshauser 才做处理 return true
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, @Nullable WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String paramToken = request.getParameter(MiaoshaUserService.TOKEN_NAME);
        String cookieToken = getCookie(MiaoshaUserService.TOKEN_NAME,request);
        if (StringUtils.isEmpty(paramToken)&&StringUtils.isEmpty(cookieToken)) return null;
        String token = StringUtils.isEmpty(cookieToken)? paramToken : cookieToken;
        MiaoshaUser user = miaoshaUserService.getByToken(token,response);
        return user;
    }

    public String getCookie(String key, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies){
            if (c.getName().equals(key)){
                return c.getValue();
            }
        }
        return null;
    }
}
