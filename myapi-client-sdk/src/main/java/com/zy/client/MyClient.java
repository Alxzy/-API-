package com.zy.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zy.model.User;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static com.zy.utils.SignUtils.getSign;


/**
 * @author Administrator
 * @version 1.0
 * @date 2024-06-01 21:45
 * 调用第三方接口的客户端
 */
public class MyClient {
    // todo 捡漏校验

    private static final String GATEWAY_HOST = "http://localhost:8090";
    private String accessKey;
    private String secretKey;

    public MyClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.get(GATEWAY_HOST + "/api/name/", paramMap);
        return result;
    }

    public String getNameByPost(String name){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.post(GATEWAY_HOST + "/api/name/", paramMap);
        return result;
    }

    private HashMap<String,String> getHeaderMap(String body){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        // 一定不能直接发送
//        hashMap.put("secretKey",secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        try {
            hashMap.put("body", URLEncoder.encode(body,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("字符转换失败");
        }
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign",getSign(body,secretKey));
        return hashMap;
    }




    public String getUserNameByPost(User user){
        String jsonStr = JSONUtil.toJsonStr(user);
        // todo user对象属性 中文乱码
        HttpResponse response = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
                .charset(StandardCharsets.UTF_8)
                .addHeaders(getHeaderMap(jsonStr))
                .body(jsonStr)
                .execute();
        System.out.println(response.getStatus());
        String result = response.body();
        return result;
    }
}
