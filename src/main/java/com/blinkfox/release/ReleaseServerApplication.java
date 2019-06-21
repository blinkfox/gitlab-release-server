package com.blinkfox.release;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 应用服务主入口类.
 *
 * @author blinkfox on 2019-06-14.
 */
@EnableConfigurationProperties
@SpringBootApplication
public class ReleaseServerApplication {

    /**
     * 应用服务主入口方法.
     *
     * @param args 数组参数
     */
    public static void main(String[] args) {
        SpringApplication.run(ReleaseServerApplication.class, args);
    }

}
