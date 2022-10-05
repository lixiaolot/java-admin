package com.lixiaolong.javaadmin.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lixiaolong.javaadmin.commom.exception.CaptchaException;
import com.lixiaolong.javaadmin.commom.lang.Const;
import com.lixiaolong.javaadmin.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String url = httpServletRequest.getRequestURI();

        if ("/login".equals(url) &&httpServletRequest.getMethod().equals("POST")){


            try {
                //            校验验证码
                validata(httpServletRequest);

            }catch (CaptchaException e){

//                放到失败处理器
                loginFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
            }

        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void validata(HttpServletRequest httpServletRequest){
//        校验验证码逻辑
        String code = httpServletRequest.getParameter("code");
        String key = httpServletRequest.getParameter("token");

        if (StringUtils.isBlank(code)|| StringUtils.isBlank(key)){
            throw new CaptchaException("验证码错误");
        }
        if (!code.equals(redisUtil.hget(Const.CAPTCHA_KEY,key))){
            throw new CaptchaException("验证码错误");
        }
//        一次性使用
        redisUtil.hdel(Const.CAPTCHA_KEY,key);
    }
}
