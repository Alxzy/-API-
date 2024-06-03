package com.zy;

import com.zy.client.MyClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 * @version 1.0
 * @date 2024-06-02 20:05
 */
@Configuration
@ConfigurationProperties("myapi.client")
@Data
@ComponentScan
public class MyApiClientConfig {
    private String accessKey;
    private String secretKey;

    @Bean
    public MyClient myApiClient(){
        return new MyClient(accessKey, secretKey);
    }
}
