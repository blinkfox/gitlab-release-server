package com.blinkfox.release.config;

import javax.annotation.Resource;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate 自动配置类.
 * <p>这里使用简单的 RestTemplate 即可.</p>
 *
 * @author blinkfox on 2019-06-15.
 */
@Configuration
public class RestTemplateConfig {

    @Resource
    private RestTemplateBuilder builder;

    /**
     * 装配 RestTemplate 实例.
     *
     * @return RestTemplate 实例
     */
    @Bean
    public RestTemplate restTemplate() {
        return builder.build();
    }

}
