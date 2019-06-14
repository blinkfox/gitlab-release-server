package com.blinkfox.release.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 的相关配置.
 *
 * @author chenjiayin on 2019-06-14.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 创建用于 Spring 管理的 Docket 实例.
     *
     * @return Docket 实例
     */
    @Bean
    public Docket newDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.blinkfox.release.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建 ApiInfo 实例.
     *
     * @return ApiInfo 实例
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("GitLab Releases 版本服务")
                .description("这是一个用于创建发布 GitLab Releases 版本的服务。")
                .termsOfServiceUrl("https://github.com/blinkfox/gitlab-release-server")
                .contact(new Contact("blinkfox", "http://blinkfox.github.io", "chenjiayin1990@163.com"))
                .version("1.0.0-SNAPSHOT")
                .build();
    }

}
