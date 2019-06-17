package com.blinkfox.release.config;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 请求拦截配置.
 *
 * @author blinkfox on 2019-05-29.
 */
@Component
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private GlobalInterceptor globalInterceptor;

    /**
     * 添加拦截器.
     *
     * @param registry 拦截器注册实例
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor);
    }

}
