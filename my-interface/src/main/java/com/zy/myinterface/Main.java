package com.zy.myinterface;

import com.zy.client.MyClient;
import com.zy.model.User;

/**
 * @author Administrator
 * @version 1.0
 * @date 2024-06-01 21:53
 */
public class Main {
    public static void main(String[] args) {
        String accessKey = "zzyy";
        String secretKey = "zzzyy";
        MyClient myClient = new MyClient(accessKey,secretKey);
        String result1 = myClient.getNameByGet("zizzy");
        String result2 = myClient.getNameByPost("zzzyy");
        User user = new User();
        user.setUsername("君梓如育");
        String result3 = myClient.getUserNameByPost(user);

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);

    }
}
