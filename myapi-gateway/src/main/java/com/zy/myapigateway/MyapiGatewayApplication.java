package com.zy.myapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyapiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyapiGatewayApplication.class, args);
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("tobaidu", r -> r.path("/baidu")
                        .uri("http://www.baidu.com"))
                .route("toyupiicu", r -> r.path("/yupiicu")
                        .uri("http://yupi.icu"))
                .build();
    }

    // 已经使用Component注解注入
//    @Bean
//    public GlobalFilter customFilter() {
//        return new CustomGlobalFilter();
//    }

}
