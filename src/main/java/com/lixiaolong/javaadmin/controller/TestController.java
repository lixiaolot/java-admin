package com.lixiaolong.javaadmin.controller;

import com.lixiaolong.javaadmin.commom.lang.Result;
import com.lixiaolong.javaadmin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    SysUserService sysUserService;
    @GetMapping("/test")
    public Object test(){
        return Result.succ(sysUserService.list()) ;
    }

}
