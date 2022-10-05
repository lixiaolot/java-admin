package com.lixiaolong.javaadmin.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.lixiaolong.javaadmin.commom.lang.Const;
import com.lixiaolong.javaadmin.commom.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Slf4j
@RestController
public class AuthController extends BaseController {

    @Autowired
    Producer producer;


    @GetMapping("/captcha")
    public Result captcha() throws IOException {


        String key = UUID.randomUUID().toString();
        String code =producer.createText(); //生成验证码 5位

        BufferedImage image = producer.createImage(code); //将验证码转换成图片

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); //流输出

        ImageIO.write(image,"jpg",outputStream);

        BASE64Encoder encoder = new BASE64Encoder();

        String str = "data:image/jpeg;base64,";//前缀
        String base64Img = str + encoder.encode(outputStream.toByteArray());
        System.out.println("验证码为"+code);

//        存入redis
        redisUtil.hset(Const.CAPTCHA_KEY,key,code,120);
        System.out.println("写入redis，写入内容的hash键为"+Const.CAPTCHA_KEY+" ，redis中的key为:"+key+" ，redis中的value为"+code);
        return Result.succ(
                MapUtil.builder()
                        .put("token",key)
                        .put("captchaImg",base64Img)
                        .build()
        );


    }
}
