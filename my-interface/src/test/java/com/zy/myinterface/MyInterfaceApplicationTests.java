package com.zy.myinterface;

import com.zy.client.MyClient;
import com.zy.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

@SpringBootTest
class MyInterfaceApplicationTests {

    @Resource
    private MyClient myClient;

    @Test
    void contextLoads() throws UnsupportedEncodingException {
        String result1 = myClient.getNameByGet("zzzzzyyy");
        User user = new User();
        user.setUsername("君梓如育");
        String result3 = myClient.getUserNameByPost(user);
        System.out.println(result1);
        System.out.println(result3);
    }

}
