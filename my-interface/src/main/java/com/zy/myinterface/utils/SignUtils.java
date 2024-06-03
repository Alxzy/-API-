package com.zy.myinterface.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;


/**
 * @author Administrator
 * @version 1.0
 * @date 2024-06-02 19:45
 * 签名工具
 */
public class SignUtils {
    /**
     * 生成签名
     * @param body
     * @param secretKey
     * @return
     */
    public static String getSign(String body,String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String content = body.toString() + "." + secretKey;
        return md5.digestHex(content);
    }

}
