package com.zy.myinterface.aop;

import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Administrator
 * @version 1.0
 * @date 2024-06-04 20:59
 * 调用次数切面
 */
@RestControllerAdvice
public class InvokeCountAOP {
    // aop 切面统计
    // 定义切面触发的时机（什么时候执行方法）controller 接口的方法执行成功后，执行下述方法
//    public void doInvokeCount(ProceedingJoinPoint point){
//
//    }
}
