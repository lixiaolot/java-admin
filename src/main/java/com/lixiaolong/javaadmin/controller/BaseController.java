package com.lixiaolong.javaadmin.controller;

import com.lixiaolong.javaadmin.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @Autowired
    HttpServletRequest req;


    @Autowired
    RedisUtil redisUtil;
}