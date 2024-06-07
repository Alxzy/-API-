package com.zy.myinterface.controller;

import com.zy.model.User;
import com.zy.myinterface.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author Administrator
 * @version 1.0
 * @date 2024-06-01 21:29
 *
 * 名称 API
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get")
    public String getNameByGet(String name, HttpServletRequest request){
//        String accessKey = request.getHeader("accessKey");
//        String secretKey = request.getHeader("secretKey");
//        if(!accessKey.equals("zzyy") || !secretKey.equals("zzzyy")){
//            throw new RuntimeException("权限错误");
//        }
        System.out.println(request.getHeader("TEST"));
        return "GET 你的名字是：" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name){
        return "POST 你的名字是：" + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request){
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String body = null;
        try {
            body = URLDecoder.decode(request.getHeader("body"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("字符转换失败");
        }
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        // todo 数据库查询 accessKey 是否已分配给用户,并得到 secretKey
        if(!accessKey.equals("zzyy")){
            throw new RuntimeException("权限错误");
        }
        // todo 随机数校验
        if(Long.parseLong(nonce) > 10000){
            throw new RuntimeException("随机数错误,无权限");
        }
        // 时间校验 例如不超过5分钟
        if(System.currentTimeMillis()/1000 - Long.parseLong(timestamp) > 300){
            throw new RuntimeException("时间戳校验失败,已超时");
        }
        // todo 实际情况中 secretKey 从数据库中查出
        String serverSign = SignUtils.getSign(body, "zzzyy");
        if(!serverSign.equals(sign)){
            throw new RuntimeException("无权限");
        }

        return "POST 用户名是：" + user.getUsername();
    }
}
