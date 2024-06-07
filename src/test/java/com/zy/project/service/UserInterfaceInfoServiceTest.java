package com.zy.project.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Administrator
 * @version 1.0
 * @date 2024-06-03 22:08
 */
@SpringBootTest
class UserInterfaceInfoServiceTest {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    public void invokeCount(){
        boolean b = userInterfaceInfoService.invokeCount(1, 1);
        Assert.assertTrue(b);
    }
}