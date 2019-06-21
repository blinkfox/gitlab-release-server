package com.blinkfox.release.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统配置.
 *
 * @author blinkfox on 2019-05-22.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "system")
public class SystemConfig {

    /**
     * 本服务的基础 URL 地址.
     */
    @Value("${system.baseUrl}")
    private String baseUrl;

    /**
     * MinIO 地址.
     */
    @Value("${system.minio.endpoint}")
    private String endpoint;

    /**
     * MinIO 用户访问名.
     */
    @Value("${system.minio.accessKey}")
    private String accessKey;

    /**
     * MinIO 用户访问密码.
     */
    @Value("${system.minio.secretKey}")
    private String secretKey;

    /**
     * MinIO 的桶.
     */
    @Value("${system.minio.bucket:releases}")
    private String bucket;

}
